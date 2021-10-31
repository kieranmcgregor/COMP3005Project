import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;


public class Server
{
	public static final String PROJECT_PATH = "/var/www/COMP3005Project/views";
	public static final int METHOD = 1;
	public static final int FILE_PATH = 2;


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
//		System.out.println(request);

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
//		System.out.println("Getting path...");

		if ("/".equals(path))
		{
			path = "html/index.html";
		}
		
		if ("/search".equals(path))
		{
		 	path = "html/search.html";
		}

		return Paths.get(PROJECT_PATH, path);
	}

	private static void sendResponse(Socket client, String status, String contentType, byte[] content) throws IOException
	{
//		System.out.println("Sending response...");

		OutputStream response = client.getOutputStream();
		response.write(("HTTP/1.1 \r\n" + status).getBytes());
		response.write(("ContentType: " + contentType + "\r\n").getBytes());
		response.write("\r\n".getBytes());
		response.write(content);
		response.write("\r\n\r\n".getBytes());
		response.flush();
		client.close();
	}

	private static void getResource(Socket client, String path) throws IOException
	{
		String contentType = "text/html";
		String status = "400";
		Path filePath = getFilePath(path);

		if (path.equals(""))
		{
			filePath = getFilePath("html/notFound.html");
			status = "405 Method Not Allowed";
		}
		else
		{
			if (Files.exists(filePath))
			{
				status = "200 OK";
			}
			else
			{
				filePath = getFilePath("html/notFound.html");
				status = "404 NotFound";
			}
		}
		
		contentType = guessContentType(filePath);
		sendResponse(client, status, contentType, Files.readAllBytes(filePath));
	}

	private static boolean authorized()
	{
		System.out.println("Checking credentials...");
		return true;
	}

	private static void postData(Socket client, String[] data) throws IOException
	{
		if (data[FILE_PATH].equals("/login"))
		{
			if (authorized())
			{
				System.out.println("Check user type...");
				getResource(client, "/search.html");
			}
		}
		else
		{
			System.out.println("Posting data...");
		}
	}

	private static void handleClient(Socket client) throws IOException
	{
//		System.out.println("Debug: got new client " + client.toString());
		
		StringBuilder builtRequest = buildRequest(client, new StringBuilder());

		String[] request = parseRequest(client, builtRequest);
		System.out.println(request[FILE_PATH]);

		if (request[METHOD].equals("GET"))
		{
			getResource(client, request[FILE_PATH]);
		}
		else if (request[METHOD].equals("POST"))
		{
			postData(client, request);
		}
		else
		{
			System.out.println("Method unknown.");
			getResource(client, "");
		}
	}
}