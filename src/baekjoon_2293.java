import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



// n가지 종류의 동전이 있음. 각가의 동전이 나타내는 가치는 다르다.
// 해당 동전들을 적당히 사용해서 해당 가치의 합이 k원이 되도록 하고싶음
// k원을 만드는 동전 구성의 경우의 수를 구한다.

// 입력 
    // n, k 입력받음 (n: 동전의 개수, k: 만들어야하는 금액)
    // n개의 줄에 각각의 동전의 가치가 주어짐

public class baekjoon_2293 {
    static int n;
    static int k;
    static int[] coins;
    static int[] dp; // 금액별로 만들 수 있는 경우의 수를 저장해놓는 배열


    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        coins = new int[n];

        for(int i=0; i<n; ++i){
            coins[i] = Integer.parseInt(br.readLine());
        }

        dp = new int[k+1];
        dp[0]=1;
        // 각 동전별로 해당 동전만 갖고 특정 금액을 만들 수 있는 경우의 수를 구하는데, 이는 이전 단계의 동전에서의 dp값을 사용해서 갱신한다.
        // 즉, 점화식은 아래와 같다
        // dp[i] = dp[i] + dp[i-coin]
        for(int i=0; i<n; ++i){
            int coin = coins[i];
            for(int j=coin; j<=k; ++j){
                dp[j] += dp[j-coin];
            }
        }
        
        System.out.println(dp[k]);
        
        br.close();
    }




   

}
