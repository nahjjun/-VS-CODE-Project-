// <문제>
// 팀 레드시프트는 대회 준비를 하다가 지루해져서 샌드박스 게임인 ‘마인크래프트’를 켰다. 
// 마인크래프트는 1 × 1 × 1(세로, 가로, 높이) 크기의 블록들로 이루어진 3차원 세계에서 
// 자유롭게 땅을 파거나 집을 지을 수 있는 게임이다.
// 목재를 충분히 모은 lvalue는 집을 짓기로 하였다. 하지만 고르지 않은 땅에는 집을 지을 수 
// 없기 때문에 땅의 높이를 모두 동일하게 만드는 ‘땅 고르기’ 작업을 해야 한다.

// lvalue는 세로 N, 가로 M 크기의 집터를 골랐다. 집터 맨 왼쪽 위의 좌표는 (0, 0)이다. 
// 우리의 목적은 이 집터 내의 땅의 높이를 일정하게 바꾸는 것이다. 우리는 다음과 같은 두 종류의
// 작업을 할 수 있다.

    // 1.좌표 (i, j)의 가장 위에 있는 블록을 제거하여 인벤토리에 넣는다.
    // 2. 인벤토리에서 블록 하나를 꺼내어 좌표 (i, j)의 가장 위에 있는 블록 위에 놓는다.

    // 1번 작업은 2초가 걸리며, 2번 작업은 1초가 걸린다. 밤에는 무서운 몬스터들이 나오기 때문에
//  최대한 빨리 땅 고르기 작업을 마쳐야 한다. ‘땅 고르기’ 작업에 걸리는 최소 시간과 그 경우 땅의
// 높이를 출력하시오.

// 단, 집터 아래에 동굴 등 빈 공간은 존재하지 않으며, 집터 바깥에서 블록을 가져올 수 없다. 
// 또한, 작업을 시작할 때 인벤토리에는 B개의 블록이 들어 있다. 땅의 높이는 256블록을 초과할 
// 수 없으며, 음수가 될 수 없다.

// <입력>
// 첫째 줄에 N, M, B가 주어진다. (1 ≤ M, N ≤ 500, 0 ≤ B ≤ 6.4 × 107)

// 둘째 줄부터 N개의 줄에 각각 M개의 정수로 땅의 높이가 주어진다. (i + 2)번째 줄의
// (j + 1)번째 수는 좌표 (i, j)에서의 땅의 높이를 나타낸다. 땅의 높이는 256보다 작거나
// 같은 자연수 또는 0이다.

// <출력>
// 첫째 줄에 땅을 고르는 데 걸리는 시간과 땅의 높이를 출력하시오. 답이 여러 개 있다면 
// 그중에서 땅의 높이가 가장 높은 것을 출력하시오.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;
public class baekjoon_18111 {
    static int N; // 집터 세로 길이
    static int M; // 집터 가로 길이
    static int B; // 작업 시작할 때 인벤토리에 있는 블록개수
    static int ground[][]; // 땅 높이 행렬
    static ArrayList<Point> list = new ArrayList<>();



    // 최소 시간 구해야함.
    static int time = Integer.MAX_VALUE;
    static int height=0;

    static class Point{
        public int row, col;
        public int height;
        public Point(int row, int col, int height){
            this.row = row;
            this.col = col;
            this.height = height;
        }
    }


    // 내가 할 수 있는 작업:
    // 1. (i,j)의 블록 제거하여 인벤토리에 넣기 (2초)
    // 2. (i,j)에 내 인벤토리의 블록 꺼내서 가장 위에 놓기 (1초)

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());

        ground = new int[N][M];

        // N x M 행렬로 땅의 높이가 주어진다.
        for(int i=0;i<N; ++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; ++j){
                ground[i][j] = Integer.parseInt(st.nextToken());
                list.add(new Point(i, j, ground[i][j]));
            }
        }
        // list 정렬 (땅 높이 기준 오름차순으로)
        list.sort(new Comparator<Point>(){
            @Override
            public int compare(Point p1, Point p2){
                return p1.height-p2.height;
            }
        });
        

        dp(0);

        System.out.println(time);
        System.out.println(height);

        
        br.close();
    }


    // <내가 생각한 방법 -> dfs>
    // 제거는 2초, 추가는 1초가 걸린다. 
    // 현재 상태에서, "가장 낮은 땅에 추가" vs "가장 높은 땅 제거" 중 한개의 작업을 수행한다.
    // 계속해서 나아가다가, 모든 땅이 같아지면 
    
    public static boolean dp(int myTime){
        // 우선 list를 정렬한다
        list.sort((p1, p2)->p1.height - p2.height);
        // 땅의 높이가 모두 같은 경우, 최솟값을 비교하고 함수를 종료한다
        if (allSame()) {
            if(myTime < time){
                time = myTime; // 최솟값 비교
                height = list.get(0).height; // 현재 높이 저장
            }
            return true;
        }

        // 만약 현재 myTime이 time을 초과했다면, 그것은 더이상 의미 없는 과정이므로 종료한다.
        if(myTime > time){
            return false;
        }

        boolean keep = false;

        // 땅의 높이가 모두 같지 않은 경우, "가장 낮은 땅에 추가" vs "가장 높은 땅 제거" 중 하나의
        // 작업을 수행한다
        
        // 가장 낮은 땅에 추가하는 작업 -> 만약 B의 개수가 1 이상이고, 가장 낮은 땅의 높이가 256이하인 경우만 수행
        Point p;
        p = list.get(0);
        if(B >= 1 && p.height<=256){    
            p.height++;
            B--;
            keep = dp(myTime+1);
            p.height--;
            B++;
        }

        // 위에거가 성공한거면, 아래 작업은 굳이 할 필요 없음(낮은 땅에 추가하는 작업이 더 비용이 적기 때문)
        if(!keep){
            // 가장 높은 땅에서 하나를 제거 -> B 개수 증가
            p = list.get(list.size()-1);
            p.height--;
            B++;
            dp(myTime+2);
            p.height++;
            B--;
        }
        return false;
    }

    // 모든 땅의 높이가 같은지 확인하는 함수
    public static boolean allSame(){
        // list의 처음, 마지막 높이가 같으면 모든 땅의 높이가 같은 것임
        if(list.get(0).height == list.get(list.size()-1).height) return true;
        else return false;
    }
}
