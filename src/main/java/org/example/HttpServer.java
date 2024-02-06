package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpServer {
    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Starting Server");
        ServerSocket servidor = new ServerSocket(35000);

        while (true) {
            Socket cliente = servidor.accept();

            /*Captura datos de entrada y salida para leer y mandar los datos*/
            try (
                    OutputStream outputStream = cliente.getOutputStream();
                    PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()))
            ) {
                String inputLine = "";
                boolean firstLine = true;
                String reqURI = "";

                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Llega: " + inputLine);
                    if (firstLine) {
                        firstLine = false;
                        reqURI = inputLine.split(" ")[1];
                    }
                    if (!in.ready()) break;
                }

                URI uri = new URI(reqURI);
                String response;

                if (uri.getPath().endsWith(".html")) {
                    response = getFileFromDisk(uri.getPath());
                    out.println(response);
                } else if (uri.getPath().endsWith(".css")) {
                    response = getFileFromDisk(uri.getPath());
                    out.println(response);
                } else if (uri.getPath().endsWith(".js")) {
                    response = getFileFromDisk(uri.getPath());
                    out.println(response);
                } else if (uri.getPath().endsWith(".jpg") || uri.getPath().endsWith(".jpeg")) {
                    response = getImageFromDisk(uri.getPath(), "image/jpeg");
                    outputStream.write(response.getBytes());
                } else if (uri.getPath().endsWith(".png")) {
                    response = getImageFromDisk(uri.getPath(), "image/png");
                    outputStream.write(response.getBytes());
                } else {
                    response = getDefaultForm();
                    out.println(response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cliente.close();
            }
        }
    }

    private static String getFileFromDisk(String filePath) {
        String contentType;
        if (filePath.endsWith(".html")) {
            contentType = "text/html";
        } else if (filePath.endsWith(".css")) {
            contentType = "text/css";
        } else if (filePath.endsWith(".js")) {
            contentType = "application/javascript";
        } else {
            return "HTTP/1.1 404 Not Found\r\n\r\n";
        }

        String output = "HTTP/1.1 200 OK \r\n" +
                "Content-Type:" + contentType + "\r\n" +
                "\r\n";
        Path file = Paths.get("target/classes/public" + filePath);
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                output += line;
            }
        } catch (IOException e) {
            return "HTTP/1.1 404 Not Found\r\n\r\n";
        }

        return output;
    }

    private static String getImageFromDisk(String filePath, String contentType) throws IOException {
        Path file = Paths.get("target/classes/public" + filePath);
        byte[] data = Files.readAllBytes(file);
        return "HTTP/1.1 200 OK \r\n" +
                "Content-Type:" + contentType + "\r\n" +
                "\r\n" +
                new String(data, Charset.forName("ISO-8859-1"));
    }

    private static String getDefaultForm() {
        return "HTTP/1.1 200 OK \r\n" +
                "Content-Type:text/html\r\n" +
                "\r\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <title>Form Example</title>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Form</h1>\n" +
                "<form action=\"/mov\">\n" +
                "  <label>Película:</label><br>\n" +
                "  <input type=\"text\" id=\"movie\" name=\"movie\" value=\"\" placeholder=\"Nombre de película...\"><br><br>\n" +
                "  <input type=\"button\" value=\"Submit\" onclick=\"loadMovie()\"><br>\n" +
                "</form>\n" +
                "<div id=\"getmovie\"></div>\n" +
                "<script>\n" +
                "  function loadMovie(){\n" +
                "    let nameVar = document.getElementById(\"movie\").value;\n" +
                "      const xhttp = new XMLHttpRequest();\n" +
                "      xhttp.onload = function() {\n" +
                "          document.getElementById(\"getmovie\").innerHTML =\n" +
                "          this.responseText;\n" +
                "      }\n" +
                "      xhttp.open(\"GET\", \"/movie?name=\"+nameVar);\n" +
                "      xhttp.send();\n" +
                "  }\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
    }
}
