
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

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        s1 = br.readLine();
        s2 = br.readLine();

        int result = solution();
        System.out.println(result);
        br.close();
    }

    public static int solution(){
        

    }

}
