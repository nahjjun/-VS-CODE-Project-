
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

// <문제>
// 차세대 영농인 한나는 강원도 고랭지에서 유기농 배추를 재배하기로 하였다. 
// 농약을 쓰지 않고 배추를 재배하려면 배추를 해충으로부터 보호하는 것이 중요하기 때문에, 
// 한나는 해충 방지에 효과적인 배추흰지렁이를 구입하기로 결심한다. 이 지렁이는 배추근처에
// 서식하며 해충을 잡아 먹음으로써 배추를 보호한다. 특히, 어떤 배추에 배추흰지렁이가 한 
// 마리라도 살고 있으면 이 지렁이는 인접한 다른 배추로 이동할 수 있어, 그 배추들 역시 
// 해충으로부터 보호받을 수 있다. 한 배추의 상하좌우 네 방향에 다른 배추가 위치한 경우에 
// 서로 인접해있는 것이다.

// 한나가 배추를 재배하는 땅은 고르지 못해서 배추를 군데군데 심어 놓았다. 배추들이 모여있는
// 곳에는 배추흰지렁이가 한 마리만 있으면 되므로 서로 인접해있는 배추들이 몇 군데에 
// 퍼져있는지 조사하면 총 몇 마리의 지렁이가 필요한지 알 수 있다. 예를 들어 배추밭이 아래와
// 같이 구성되어 있으면 최소 5마리의 배추흰지렁이가 필요하다. 0은 배추가 심어져 있지 않은 
// 땅이고, 1은 배추가 심어져 있는 땅을 나타낸다.


// <입력>
// 입력의 첫 줄에는 테스트 케이스의 개수 T가 주어진다. 그 다음 줄부터 각각의 테스트 케이스에
// 대해 첫째 줄에는 배추를 심은 배추밭의 가로길이 M(1 ≤ M ≤ 50)과 세로길이 N(1 ≤ N ≤ 50),
// 그리고 배추가 심어져 있는 위치의 개수 K(1 ≤ K ≤ 2500)이 주어진다. 그 다음 K줄에는 
// 배추의 위치 X(0 ≤ X ≤ M-1), Y(0 ≤ Y ≤ N-1)가 주어진다. 두 배추의 위치가 같은 경우는
// 없다.

// <출력>
// 각 테스트 케이스에 대해 필요한 최소의 배추흰지렁이 마리 수를 출력한다.


public class baekjoon_1012 {
    static BufferedReader br;
    static StringTokenizer st;
    static int M, N, K;

    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        
        int T = Integer.parseInt(br.readLine());
        

        // 테스트 케이스 횟수만큼 solution 진행
        for(int i=0; i<T; ++i){
            st = new StringTokenizer(br.readLine());
            M  = Integer.parseInt(st.nextToken());
            N  = Integer.parseInt(st.nextToken());
            K  = Integer.parseInt(st.nextToken());
            solution(N, M, K);
        }

        br.close();
    }


    public static void solution(int row, int col, int K) throws IOException{
        // Point들을 입력받기 위한 Queue, 실시간 배추밭 상황을 알기 위한 2차원 배열 생성
        // 배추밭은 배추가 없으면 -1, 있으면 0으로 설정한다.

        Queue<Point> queue = new ArrayDeque<>();
        int[][] list = new int[row][col];
        for(int i=0; i<row; ++i){
            Arrays.fill(list[i], -1);
        }

        // K번만큼 배추 Point들을 입력받음
        for(int i=0; i<K; ++i){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            Point p = new Point(x, y);
            queue.add(p);

            // 해당 포인트의 list 좌표를 0으로 설정
            list[y][x] = 0;
        }

        // 이제 queue에는 각 배추가 위치한 Point객체들이, 그리고 list에는 각 배추의
        // 위치들이 적혀있다. 이제 queue의 요소들을 순회하며 dfs로 list를 최신화해주며 
        // 지렁이의 개수를 증가시켜주면 된다.
        
        int num = 0; // 지렁이 개수

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int x = (int)p.getX();
            int y = (int)p.getY();

            // 해당 위치가 아직 조회가 안된 곳이라면, num을 증가시켜주고, 해당 위치를 기점으로
            // 연결된 곳들을 전부 num으로 설정해준다
            if(list[y][x] == 0){
                num++;
                dfs(num, list, x, y);
            }

        }
        System.out.println(num);
    }


    public static void dfs(int num, int[][] list, int x, int y){
        // 범위 벗어나거나 list 좌표값이 0이 아닌 경우, 해당 회차 종료
        if(x<0 || x>=M || y<0 || y>=N || list[y][x] != 0){
            return;
        }
        int dx[] = {0, 0, 1, -1};
        int dy[] = {1, -1, 0, 0};

        list[y][x] = num;
        for(int i=0; i<4; ++i){
            dfs(num, list, x+dx[i], y+dy[i]);
        }
    }
}
