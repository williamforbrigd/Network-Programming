const net = require("net");
const crypto = require("crypto");

// Simple HTTP server responds with a simple WebSocket client test
const httpServer = net.createServer((connection) => {
  connection.on("data", () => {
    let content = `<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
  </head>
  <body>
    WebSocket test page
    <script>
      let ws = new WebSocket('ws://localhost:3001');
      ws.onmessage = event => alert('Message from server: ' + event.data);
      ws.onopen = () => ws.send('hello');
    </script>
  </body>
</html>
`;
    connection.write(
      "HTTP/1.1 200 OK\r\nContent-Length: " +
        content.length +
        "\r\n\r\n" +
        content
    );
  });
});
httpServer.listen(3000, () => {
  console.log("HTTP server listening on port 3000");
});

let clients = [];
const wsServer = net.createServer((connection) => {
  console.log("Client connected");

  connection.on("data", (data) => {
    console.log("Data received from client: ", data.toString());
    let key = "";
    if ((key = hasKey(data.toString())) != null) {
      let acceptString = base64encode(sha1(key));

      connection.write("HTTP/1.1 101 Switching Protocols\r\n");
      connection.write("Upgrade: websocket\r\n");
      connection.write("Connection: Upgrade\r\n");
      connection.write("Sec-WebSocket-Accept: " + acceptString + "\r\n\r\n");
      clients.push(connection);
      console.log("websocket connected");
    } else {
      let msg = decodeMessage(data);
      console.log("Data recieved from client: " + msg);

      let response = createBufferFromMessage(msg);
      sendToClients(response, connection);
      console.log(response);
    }
  });

  connection.on("end", () => {
    console.log("Client disconnected");
  });
});

wsServer.on("error", (error) => {
  console.error("Error: ", error);
});
wsServer.listen(3001, () => {
  console.log("WebSocket server listening on port 3001");
});

//Method to check if the data has the key and if it is contained only once.
function hasKey(data) {
  let split = data.split(" ");
  let count = 0;
  let index = -1;
  for (let i = 0; i < split.length; i++) {
    if (split[i].includes("Sec-WebSocket-Key")) {
      index = i + 1;
      count++;
    }
  }
  if (count == 1 && index != -1) return split[index].split("\n")[0].trim();
  else return null;
}

function sha1(key) {
  const rfc6455 = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
  return crypto.createHash("sha1").update(key + rfc6455, "binary");
}

function base64encode(hashed) {
  return hashed.digest("base64");
}

function decodeMessage(data) {
  let bytes = Buffer.from(data);

  //bytes[0] is the type of message
  let length = bytes[1] & 127; //bytes[1] is the length of the message
  let maskStart = 2; //next four bytes are the mask starting at index 2
  let dataStart = maskStart + 4;
  let msg = "";
  for (let i = dataStart; i < dataStart + length; i++) {
    let byte = bytes[i] ^ bytes[maskStart + ((i - dataStart) % 4)];
    msg += String.fromCharCode(byte);
  }
  return msg;
}

function createBufferFromMessage(message) {
  let sub1 = Buffer.from([0x81, message.length]);
  let sub2 = Buffer.from(message, "utf-8");
  return Buffer.concat([sub1, sub2]);
}

function sendToClients(buffer, connection) {
  for (let i = 0; i < clients.length; i++)
    if (connection != clients[i]) clients[i].write(buffer); //Send the message to all other clients, not the current connection.
}
