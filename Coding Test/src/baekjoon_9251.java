
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



// <문제>
// LCS(Longest Common Subsequence, 최장 공통 부분 수열)문제는 두 수열이 주어졌을 때,
// 모두의 부분 수열이 되는 수열 중 가장 긴 것을 찾는 문제이다.

// 예를 들어, ACAYKP와 CAPCAK의 LCS는 ACAK가 된다.

// <입력>
// 첫째 줄과 둘째 줄에 두 문자열이 주어진다. 문자열은 알파벳 대문자로만 이루어져 있으며, 
// 최대 1000글자로 이루어져 있다.

// <출력>
// 첫째 줄에 입력으로 주어진 두 문자열의 LCS의 길이를 출력한다.

public class baekjoon_9251 {
    static String s1, s2;
    static int[][] dp;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        s1 = br.readLine();
        s2 = br.readLine();

        dp = new int[s1.length()+1][s2.length()+1];

        int result = solution();
        System.out.println(result);
        br.close();
    }

    // 순서도 고려해야한다. DP 문제
    public static int solution(){
        for(int i=1; i<=s1.length(); ++i){
            for(int j=1; j<=s2.length(); ++j){
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1]+1;
                } else {
                    // 같지 않은 경우, 이전 두 값 중 더 큰 값 가져옴
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
                }

            }
        }
        return dp[s1.length()][s2.length()];
    }

}
