
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


// <문제>
// 이 문제는 아주 평범한 배낭에 관한 문제이다.

// 한 달 후면 국가의 부름을 받게 되는 준서는 여행을 가려고 한다. 세상과의 단절을 슬퍼하며 
// 최대한 즐기기 위한 여행이기 때문에, 가지고 다닐 배낭 또한 최대한 가치 있게 싸려고 한다.

// 준서가 여행에 필요하다고 생각하는 N개의 물건이 있다. 각 물건은 무게 W와 가치 V를 가지는데,
// 해당 물건을 배낭에 넣어서 가면 준서가 V만큼 즐길 수 있다. 아직 행군을 해본 적이 없는 
// 준서는 최대 K만큼의 무게만을 넣을 수 있는 배낭만 들고 다닐 수 있다. 준서가 최대한 즐거운 
// 여행을 하기 위해 배낭에 넣을 수 있는 물건들의 가치의 최댓값을 알려주자.

// <입력>
// 첫 줄에 물품의 수 N(1 ≤ N ≤ 100)과 준서가 버틸 수 있는 무게 K(1 ≤ K ≤ 100,000)가
// 주어진다. 두 번째 줄부터 N개의 줄에 거쳐 각 물건의 무게 W(1 ≤ W ≤ 100,000)와 해당 
// 물건의 가치 V(0 ≤ V ≤ 1,000)가 주어진다.

// 입력으로 주어지는 모든 수는 정수이다.

// <출력>
// 한 줄에 배낭에 넣을 수 있는 물건들의 가치합의 최댓값을 출력한다.


// 대표적인 dp 문제이다. 물건 x 배낭 크기 2차원 배열을 생성하여 해당 배열에서 각 행 기준으로
// 이전 값을 참고하며 "가치값"을 채워나가는 문제이다.
public class baekjoon_12865 {
    static int N, K;
    static int[][] list; // 각 물건들의 무게, 가치가 담겨있는 배열
    static int[][] dp; // dp에 사용될 2차원 배열. 물건의 가치값들이 담길 배열이다.


    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        list = new int[N][2];
        for(int i=0; i<N; ++i){
            st = new StringTokenizer(br.readLine());

            list[i][0] = Integer.parseInt(st.nextToken()); // 물건 무게 W
            list[i][1] = Integer.parseInt(st.nextToken()); // 물건 가치 V
        }

        // dp 배열 생성
        dp = new int[N+1][K+1];

        dp();

        System.out.println(dp[N][K]);
        br.close();
    }

    public static void dp(){
        // 1 ~ N까지의 행까지 검사 진행
        for(int i=1; i<=N; ++i){
            // 각 물건의 무게, 가치
            int weight = list[i-1][0], value = list[i-1][1];

            // weight ~ K까지 해당 행 채우기 (이전 행 참고해서)
            for(int j=0; j<=K; ++j){
                // j가 weight보다 작은 경우, 이전 값 그대로 가져오기
                if(j < weight) 
                    dp[i][j] = dp[i-1][j];
                else
                    dp[i][j] = Math.max(dp[i-1][j-weight]+value, dp[i-1][j]);
            }
        }
    }
    
}
