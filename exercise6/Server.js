const net = require("net");
//testing something

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

// Incomplete WebSocket server
const wsServer = net.createServer((connection) => {
  console.log("Client connected");

  const rfc6455_constant = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

  connection.on("data", (data) => {
    let dataStr = data.toString();
    console.log("Data received from client: \n", dataStr);
    if (dataStr.includes("Sec-WebSocket-Key")) {
      console.log("yressir");
      //TODO: convert to sha1
    }
  });

  connection.on("end", () => {
    console.log("Client disconnected");
  });
});
wsServer.on("data", (data) => {
  console.log(data.toString());
});

wsServer.on("error", (error) => {
  console.error("Error: ", error);
});
wsServer.listen(3001, () => {
  console.log("WebSocket server listening on port 3001");
});

function sha1(data) {
  const crypto = require("crypto");
  //let hash = crypto.getHashes();
  let hashPwd = crypto.createHash("sha1").update(data).digest("hex");
  return hashPwd;
}

let hashed = sha1(
  "x3JJHMbDL1EzLkh9GBhXDw" + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11"
);
console.log(hashed);

//Convert to base64encode
console.log(Buffer.from(hashed).toString("base64"));
