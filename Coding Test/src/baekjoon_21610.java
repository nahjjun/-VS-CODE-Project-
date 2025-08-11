
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;



// 오늘 새로 배운 마법은 A(비바라기)임 -> 하늘에 비구름 만들 수 있음
// A 마법을 N x N 격자에서 실행함. 각 칸에 저장할 수 있는 물의 양에는 제한이 없음
// (r, c)는 격자의 r행 c열에 있는 바구니를 의미. A[r][c]는 바구니에 저장되어있는 물의 양을 의미
// 1번 행과 N번 행은 연결되어있으며, 1번 열과 N번 열 또한 연결되어있다.

// A 마법 실행 시, (N,1), (N,2), (N-1, 1), (N-1, 2)에 비구름이 생김
// 구름은 M번 이동한다. i번째 이동 명령은 방향(d_i)와 거리(s_i)
    // 방향
    // d_1: ←, d_2: ↖, d_3: ↑, d_4: ↗, d_5: →, d_6: ↘, d_7: ↓, d_8: ↙ 

// 이동을 명령하면, 아래 순서대로 과정이 진행된다
    // 1. 모든 구름이 d_i 방향으로 s_i칸 이동한다
    // 2. 각 구름에서 비가 내려서 구름이 있는 칸에 저장된 물의 양이 1 증가함
    // 3. 구름이 전부 사라진다
    // 4. 2번 단계에서 물이 증가한 칸 (r, c)에 물복사버그 마법 시전
    //      ㄴ> 물복사 버그 사용 시, 1~N 범위를 넘어가는 대각선 칸은 마법이 적용되지 않는다.
    // 5. 물을 증가시킨 후, 해당 칸에 저장된 물의 양이 2 이상인 칸은 물의 수위가 2가 줄어들며
    //      ㄴ> 이때, 이 구름이 생기는 칸은 3번 단계에서 구름이 사라진 칸이 아니어야 함

// M번의 명령이 끝난 이후, 바구니들에 들어있는 물의 양의 합을 출력함


public class baekjoon_21610 {
    static int N; // N x N의 격자 칸
    static int M; // M 번의 이동 명령
    static int[][] list; // N x N 형태 격자. 물이 얼마나 차있는지 저장하고있음
    static int[] d; // 이동 방향
    static int[] s; // 이동 거리

    static int[][] prev; // 3단계에서 구름이 사라진 칸인지 아닌지 판별하기 위한 배열
    static int round; // 해당 라운드 설정
    static ArrayDeque<Point> cloud; // 구름이 있는 위치가 들어있는 큐

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        list = new int[N][N];

        for(int i=0; i<N; ++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; ++j){
                list[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 입력받기
        d = new int[M];
        s = new int[M];
        for(int i=0; i<M; ++i){
            st = new StringTokenizer(br.readLine());
            d[i] = Integer.parseInt(st.nextToken());
            s[i] = Integer.parseInt(st.nextToken());
        }

        // prev, round 초기화
        prev = new int[N][N];
        round = 1;

        // cloud 큐 초기화
        cloud = new ArrayDeque<>();
        cloud.add(new Point(1, N));
        cloud.add(new Point(2, N));
        cloud.add(new Point(1, N-1));
        cloud.add(new Point(2, N-1));
        
        for(int i=0; i<M; ++i){
            solution(d[i], s[i]);
            round++;
        }
        
        int result = 0;
        for(int i=0; i<N; ++i){
            for(int j=0; j<N; ++j){
                result += list[i][j];
            }
        }
        System.out.println(result);

        br.close();
    }

    public static void solution(int D, int S){
        // 1. cloud에 있는 구름들을 모두 d 방향으로 s칸 이동시킴
        int length = cloud.size();
        for(int i=0; i<length; ++i){
            Point p = cloud.pollFirst();
            p = move(D, S, p);
            cloud.addLast(p);

            // 2. 움직인 위치의 물의 양을 1 증가시킴
            int x = (int)p.getX();
            int y = (int)p.getY();
            list[y][x]++;

            // 3. 해당 위치의 구름이 사라지지만, 이를 prev 배열에 기록한다.
            prev[y][x] = round;
        }

        // 4. cloud에 들어 있는 좌표들 (물이 증가한 칸)에 물복사버그 마법을 시전한다.
        // 해당 좌표에서 대각선 방향 (4방향)의 1 거리인 칸에 물이 있는 바구니 개수만큼 
        // 해당 좌표의 물 수위가 높아진다.
        for(Point p : cloud){
            int x = (int)p.getX();
            int y = (int)p.getY();
            // 대각선 위치들 확인
            if(x-1 >= 0 && x-1<N && y-1>=0 && y-1<N && list[y-1][x-1] >= 1){
                list[y][x]++;
            }
            if(x+1 >= 0 && x+1<N && y-1>=0 && y-1<N && list[y-1][x+1] >= 1){
                list[y][x]++;
            }
            if(x-1 >= 0 && x-1<N && y+1>=0 && y+1<N && list[y+1][x-1] >= 1){
                list[y][x]++;
            }
            if(x+1>= 0 && x+1<N && y+1>=0 && y+1<N && list[y+1][x+1] >= 1){
                list[y][x]++;
            }
        }
        
        // 5. 배열 전체를 확인하며 물의 양이 2 이상인 칸에 구름을 생성한다. 해당 좌표의
        // 물 수위는 2 낮춘다. 이때, prev[][]좌표값이 round와 같은 곳은 그냥 지나친다
        for(int i=0; i<N; ++i){
            for(int j=0; j<N; ++j){
                if(prev[i][j] == round) continue;

                if(list[i][j] >= 2){
                    cloud.addLast(new Point(j, i));
                    list[i][j] -= 2;
                }
            }
        }


    }

    // 주어진 방향으로 S만큼 이동한 후의 Point를 return해주는 함수
    public static Point move(int D, int S, Point p){
        int dx = 0;
        int dy = 0;
        switch(D){
            case 1:
                dx = -1;
                break;
            case 2:
                dx = -1;
                dy = -1;
                break;
            case 3:
                dy = -1;
                break;
            case 4:
                dx = 1;
                dy = -1;
                break;
            case 5:
                dx = 1;
                break;
            case 6:
                dx = 1;
                dy = 1;
                break;
            case 7:
                dy = 1;
                break;
            case 8:
                dx = -1;
                dy = 1;
                break;
        }
        dx *= S;
        dy *= S;
        return new Point((int)p.getX()+dx, (int)p.getY()+dy);
    }


}
