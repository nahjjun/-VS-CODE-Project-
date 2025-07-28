
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

// 문자열 S가 주어졌을 때, S의 서로 다른 부분 문자열의 개수를 구하는 프로그램을 작성하시오.

// 부분 문자열은 S에서 연속된 일부분을 말하며, 길이가 1보다 크거나 같아야 한다.

// 예를 들어, ababc의 부분 문자열은 a, b, a, b, c, ab, ba, ab, bc, aba, bab, abc, abab, babc, ababc가 있고, 서로 다른것의 개수는 12개이다.


// <입력>
// 첫째 줄에 문자열 S가 주어진다. S는 알파벳 소문자로만 이루어져 있고, 길이는 1,000 이하이다.

// <출력>
// 첫째 줄에 S의 서로 다른 부분 문자열의 개수를 출력한다.

public class baekjoon_11478 {
    static String s;
    static HashSet<String> set = new HashSet<>();


    // StringBuilder로 string 갖기
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        s = br.readLine();

        StringBuilder sb = new StringBuilder();
        int makeNum = 1; // 몇개의 문자를 선택할 것인지

        // 선택하는 문자의 개수가 문자열 길이를 넘으면 반복문 종료
        while(makeNum <= s.length()){
            for(int i=0; i+makeNum<=s.length(); i++){ // 선택하는 문자의 개수만큼 해당 배열에서 순서대로 단어를 추가해준다.
                sb.setLength(0); // sb 초기화
                for(int j=0; j<makeNum; ++j){
                    sb.append(s.charAt(i+j));
                }
                set.add(sb.toString());
            }

            makeNum++;
        }

        System.out.println(set.size());
    }


}
