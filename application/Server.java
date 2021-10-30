import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;


public class Server
{
	public static final String PROJECT_PATH = "/var/www/COMP3005Project/views";

    public static void main(String[] args) throws Exception
    {
        try (ServerSocket serverSocket = new ServerSocket(8080))
        {
            while (true)
            {
                try (Socket client = serverSocket.accept())
                {
                    handleClient(client);
                }
            }
        }        
        catch (IOException ex)
        {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static StringBuilder buildRequest(Socket client, StringBuilder request) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String line;

        while (!(line = br.readLine()).isBlank())
        {
            request.append(line + "\r\n");
        }

        return request;
    }

    private static String[] parseRequest(Socket client, StringBuilder builtRequest)
    {
    	String request = builtRequest.toString();
		System.out.println(request);

        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        String method = requestLine[0];
        String path = requestLine[1];
        String version = requestLine[2];
        String host = requestsLines[1].split(" ")[1];

        List<String> headers = new ArrayList<>();

        for (int h = 2; h <requestsLines.length; ++h)
        {
            String header = requestsLines[h];
            headers.add(header);
        }
/*
        String accessLog = String.format("Client %s\n method %s\n path %s\n version %s\n host %s\n headers %s\n"
                                            , client.toString()
                                            , method
                                            , path
                                            , version
                                            , host
                                            , headers.toString());
        System.out.println(accessLog);
*/
        String[] sa = {client.toString(), method, path, version, host, headers.toString()};

        return sa;
    }

	private static String guessContentType(Path filePath) throws IOException
	{
		System.out.println("Guessing content type...");

		return Files.probeContentType(filePath);
	}

	private static Path getFilePath(String path)
	{
		System.out.println("Getting path...");

		if ("/".equals(path))
		{
			path = "html/index.html";
		}
		
		// if ("/test".equals(path))
		// {
		// 	path = "html/test.html";
		// }

		return Paths.get(PROJECT_PATH, path);
	}

	private static void sendResponse(Socket client, String status, String contentType, byte[] content) throws IOException
	{
		System.out.println("Sending response...");

		OutputStream response = client.getOutputStream();
		response.write(("HTTP/1.1 \r\n" + status).getBytes());
		response.write(("ContentType: " + contentType + "\r\n").getBytes());
		response.write("\r\n".getBytes());
		response.write(content);
		response.write("\r\n\r\n".getBytes());
		response.flush();
		client.close();
	}

    private static void handleClient(Socket client) throws IOException
    {
//        System.out.println("Debug: got new client " + client.toString());
		String contentType;
		String status = "400";
        StringBuilder builtRequest = buildRequest(client, new StringBuilder());

        String[] request = parseRequest(client, builtRequest);
        System.out.println(request[2]);

		Path filePath = getFilePath(request[2]);

		if (Files.exists(filePath))
		{
			status = "200 OK";
		}
		else
		{
			filePath = getFilePath("html/notFound.html");
			status = "404 NotFound";
		}

		contentType = guessContentType(filePath);
		sendResponse(client, status, contentType, Files.readAllBytes(filePath));
    }
}