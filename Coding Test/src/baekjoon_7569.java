
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;



public class baekjoon_7569 {
    static int M=0, N=0, H=0; // 각각 상자의 가로, 세로 길이, 높이(쌓아올려지는 상자의 수)
    static int[][][] tomato; // 토마토 리스트
    static int max=0;    
    static int undone = 0; // 아직 안익은 토마토 개수

    public static class Node{
        public int row, col, height;
        public Node(int r, int c, int h){
            row = r;
            col = c;
            height = h;
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        // 토마토 배열 입력받기
        tomato = new int[H][N][M];
        for(int h=0; h<H; ++h){
            for(int n=0; n<N; ++n){
                st = new StringTokenizer(br.readLine());
                for(int m=0; m<M; ++m){
                    int tmp = Integer.parseInt(st.nextToken());
                    tomato[h][n][m] = tmp;
                    if(tmp == 0) undone++;
                }        
            }
        }

        int result = solution();
        System.out.println(result);
        br.close();
    }

    // 몇일 걸리는지 반환하는 함수
    public static int solution(){
        int dx[] = {-1, 1, 0, 0, 0, 0};
        int dy[] = {0, 0, -1, 1, 0, 0};
        int dz[] = {0, 0, 0, 0, -1, 1};

        ArrayDeque<Node> queue = new ArrayDeque<>();
        // queue에 현재 1인 곳의 좌표를 전부 넣는다.
        for(int h=0; h<H; ++h){
            for(int n=0; n<N; ++n){
                for(int m=0; m<M; ++m){
                    if(tomato[h][n][m] == 1){
                        queue.add(new Node(n, m, h));
                    }
                }
            }
        }


        // 큐에서 한 좌표를 꺼냈을 때, 해당 좌표의 6방향에 있는 0을 찾아서, 해당 값을 현재
        // 좌표 +1 값으로 바꿔준다. 그리고 바꿔준 그 값을 max와 비교해서 최신화시켜준다.
        while(!queue.isEmpty()){
            Node node = queue.poll();
            int h = node.height;
            int n = node.row;
            int m = node.col;
            
            // 1이상인 숫자를 만났을 때, 해당 숫자의 상하좌우값을 현재값+1로 바꿔준다.
            if(tomato[h][n][m] >= 1){
                for(int i=0; i<6; ++i){
                    if(n+dy[i] < 0 || n+dy[i] >= N || m+dx[i] < 0 || m+dx[i] >= M || h+dz[i] < 0 || h+dz[i]>=H) continue;
                    if(tomato[h+dz[i]][n+dy[i]][m+dx[i]] == 0){
                        tomato[h+dz[i]][n+dy[i]][m+dx[i]] = tomato[h][n][m]+1;
                        if(max < tomato[h][n][m]) max = tomato[h][n][m];
                        --undone; // 남은 안익은 토마토 개수 갱신
                        // 해당 좌표 Node 만들어서 큐에 삽입
                        queue.add(new Node(n+dy[i], m+dx[i], h+dz[i]));
                    }
                }
            }
        }        

        // 만약 반복문이 끝났는데, undone이 1 이상이면 -1 반환
        // 아닌 경우, max-1을 반환
        if(undone >= 1) return -1;
        else return max;
    }

}
