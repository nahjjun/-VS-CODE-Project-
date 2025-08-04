
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

// <문제>
// 민식이와 준영이는 자기 방에서 문자열을 공부하고 있다. 민식이가 말하길 인접해 
// 있는 모든 문자가 같지 않은 문자열을 행운의 문자열이라고 한다고 한다. 준영이는
// 문자열 S를 분석하기 시작했다. 준영이는 문자열 S에 나오는 문자를 재배치하면 서로
// 다른 행운의 문자열이 몇 개 나오는지 궁금해졌다. 만약 원래 문자열 S도 행운의
// 문자열이라면 그것도 개수에 포함한다.

// <입력>
// 첫째 줄에 문자열 S가 주어진다. S의 길이는 최대 10이고, 알파벳 소문자로만 이루어져 있다.

// <출력>
// 첫째 줄에 위치를 재배치해서 얻은 서로 다른 행운의 문자열의 개수를 출력한다.
public class baekjoon_1342 {
    static String S;
    static HashMap<Character, Integer> map;
    static int result;
    static ArrayList<Character> keyList;


    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        map = new HashMap<Character, Integer>();

        S = br.readLine();

        // map에 각 알파벳별 개수 넣어놓기
        for(int i=0; i<S.length(); ++i){
            // 갖고 있지 않은 경우
            if(!map.containsKey(S.charAt(i))){
                map.put(S.charAt(i), 1);
            } else {
                map.put(S.charAt(i), map.get(S.charAt(i))+1);
            }
        }
        
        // map에 있는 key들을 전부 가져옴
        keyList = new ArrayList<>();
        for(Character key : map.keySet()){
            keyList.add(key);
        }
        // 
        result = 0;
        dfs(0, 'A');

        System.out.println(result);
        br.close();
    }


    public static void dfs(int length, Character prev){
        if(length == S.length()){
            result++;
            return;
        }

        for(int i=0; i<keyList.size(); ++i){
            Character c = keyList.get(i);
            // 해당 key 알파벳의 개수가 0보다 크고, 이전에 사용한 알파벳과 같지 않다면, 해당 알파벳으로 뻗어나감
            if(map.get(c) > 0 && !c.equals(prev)){
                map.put(c, map.get(c)-1);
                dfs(length+1, c);
                map.put(c, map.get(c)+1);
            }
        }
    }

}
