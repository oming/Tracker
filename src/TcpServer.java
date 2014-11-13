import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 클라이언트의 연결을 기다리는 서버 쓰레드를 생성합니다.
 * 
 * @author An
 *
 */
public class TcpServer implements Runnable {
	ServerSocket serverSocket;
	Thread[] threadArr;
	ConnectDB idb = new ConnectDB();

	public static void main(String[] args) {
		// 5개의 쓰레드를 생성하는 서버를 생성한다.
		
		TcpServer server = new TcpServer(5);
		server.start();
		
	}

	public TcpServer(int num) {
		try {
			// 서버 소켓을 생성하여 7777번 포트와 바인딩.
			serverSocket = new ServerSocket(7777);
			System.out.println(getTime() + " 서버가 준비되었습니다.");
			
			idb.ConnectDatabase();		// 서버의 DB에 접속함.
			System.out.println(getTime() + " DB 접속을 완료하였음.");
			threadArr = new Thread[num];
			idb.CloseDatabase();
			System.out.println(getTime() + " DB 접속을 종료합니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void start() {
		for (int i = 0; i < threadArr.length; i++) {
			threadArr[i] = new Thread(this);
			threadArr[i].start();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println(getTime() + " 가 연결 요청을 기다립니다.");

				Socket socket = serverSocket.accept();
				System.out.println(getTime() + " " + socket.getInetAddress()
						+ "로부터 연결요청이 들어왔습니다.");

				OutputStream out = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(out);

				dos.writeUTF("[Notice] Test Message1 from Server");
				System.out.println(getTime() + " 데이터를 전송하였습니다.");

				dos.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static String getTime() {
		String name = Thread.currentThread().getName();
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date()) + name;
	}
}
