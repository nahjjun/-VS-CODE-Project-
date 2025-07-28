// <문제>
// 상근이의 여동생 상냥이는 문방구에서 스티커 2n개를 구매했다. 스티커는 그림 (a)와
// 같이 2행 n열로 배치되어 있다. 상냥이는 스티커를 이용해 책상을 꾸미려고 한다.

// 상냥이가 구매한 스티커의 품질은 매우 좋지 않다. 스티커 한 장을 떼면, 그 스티커와
//  변을 공유하는 스티커는 모두 찢어져서 사용할 수 없게 된다. 즉, 뗀 스티커의 왼쪽, 
// 오른쪽, 위, 아래에 있는 스티커는 사용할 수 없게 된다.

// 모든 스티커를 붙일 수 없게된 상냥이는 각 스티커에 점수를 매기고, 점수의 합이 최대가
//  되게 스티커를 떼어내려고 한다. 먼저, 그림 (b)와 같이 각 스티커에 점수를 매겼다.
// 상냥이가 뗄 수 있는 스티커의 점수의 최댓값을 구하는 프로그램을 작성하시오. 즉, 2n개의
// 스티커 중에서 점수의 합이 최대가 되면서 서로 변을 공유 하지 않는 스티커 집합을 구해야 
// 한다.

// 위의 그림의 경우에 점수가 50, 50, 100, 60인 스티커를 고르면, 점수는 260이 되고 이
//  것이 최대 점수이다. 가장 높은 점수를 가지는 두 스티커 (100과 70)은 변을 공유하기
// 때문에, 동시에 뗄 수 없다.


// <입력>
// 첫째 줄에 테스트 케이스의 개수 T가 주어진다. 각 테스트 케이스의 첫째 줄에는 
// n (1 ≤ n ≤ 100,000)이 주어진다. 다음 두 줄에는 n개의 정수가 주어지며, 각
//  정수는 그 위치에 해당하는 스티커의 점수이다. 연속하는 두 정수 사이에는 빈 칸이
// 하나 있다. 점수는 0보다 크거나 같고, 100보다 작거나 같은 정수이다.

// <출력>
// 각 테스트 케이스 마다, 2n개의 스티커 중에서 두 변을 공유하지 않는 스티커 점수의 
// 최댓값을 출력한다.


// dp를 이용하는 문제라고 판단함. 각 스티커 사용 가능 여부를 갱신하면서 더이상 사용 가능한 
// 스티커가 없을 때 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class baekjoon_9465{
    static int T;
    static int n; // 열의 길이
    static int score[][]; // 점수 배열
    static int memo[][]; // 메모제이션 적용을 위한 배열

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        T = Integer.parseInt(br.readLine());
        
        for(int a=0; a<T; ++a){
            n = Integer.parseInt(br.readLine());
            score = new int[2][n];
            memo = new int[2][n];
            for(int i=0; i<2; ++i){
                Arrays.fill(memo[i], -1);
            }
            
            // 스티커 점수 입력받음
            for(int i=0; i<2; ++i){
                st = new StringTokenizer(br.readLine());
                for(int j=0; j<n; ++j){
                    score[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int dp1 = dp(0,0);
            int dp2 = dp(1,0);
            int max = Integer.max(dp1, dp2);

            System.out.println(max);
        }
        br.close();
    }

    // <DP 문제 풀 때 팁>
    // dp를 풀 때, Top-Down 방식과 Bottom-Up방식이 있다. 두 경우를 모두 고려해봐야한다.
    // DP를 풀 때는 점화식을 만들어야 하는데, "A일 때 B한 C값을 찾아라"로 생각해보자.
    // 점화식이란, 큰 문제의 해답을 더 작은 문제의 해답으로 표현한 수식
    
    // 시작 행을 입력받으면, 해당 경우의 점수를 출력한다.
    // 0번 행의 i를 선택했으면, 고를 수 있는 경우의 수는 1번 행의 i+1 / i+2이다
    // 0번 행의 i를 선택 안했으면, 고를 수 있는 경우의 수는 0번 행의 i+1이다
    // 두 경우 중 더 큰 값을 사용하면서 
    public static int dp(int currentRow, int currentCol){
        if(currentCol >= n){
            return 0;
        }
        // 이미 점수가 계산된 경우면, 그냥 그 값을 반환
        if(memo[currentRow][currentCol] != -1) return memo[currentRow][currentCol];

        // 현재거를 선택하는 경우
        int take = score[currentRow][currentCol] + Integer.max(dp((currentRow+1)%2, currentCol+1), dp((currentRow+1)%2, currentCol+2));
    
        // 현재거를 선택 안하는 경우
        int skip = dp(currentRow, currentCol+1);

        // 두 경우의 수 중 더 큰 것을 골라서 memo에 저장 및 반환
        return memo[currentRow][currentCol] = Integer.max(take, skip);
    }

}
