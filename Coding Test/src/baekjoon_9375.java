
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

// <문제>
// 해빈이는 패션에 매우 민감해서 한번 입었던 옷들의 조합을 절대 다시 입지 않는다.
// 예를 들어 오늘 해빈이가 안경, 코트, 상의, 신발을 입었다면, 다음날은 바지를 추가로
// 입거나 안경대신 렌즈를 착용하거나 해야한다. 해빈이가 가진 의상들이 주어졌을때 과연
// 해빈이는 알몸이 아닌 상태로 며칠동안 밖에 돌아다닐 수 있을까?

// <입력>
// 첫째 줄에 테스트 케이스가 주어진다. 테스트 케이스는 최대 100이다.
// 각 테스트 케이스의 첫째 줄에는 해빈이가 가진 의상의 수 n(0 ≤ n ≤ 30)이 주어진다.
// 다음 n개에는 해빈이가 가진 의상의 이름과 의상의 종류가 공백으로 구분되어 주어진다. 같은 종류의 의상은 하나만 입을 수 있다.
// 모든 문자열은 1이상 20이하의 알파벳 소문자로 이루어져있으며 같은 이름을 가진 의상은 존재하지 않는다.

// <출력>
// 각 테스트 케이스에 대해 해빈이가 알몸이 아닌 상태로 의상을 입을 수 있는 경우를 출력하시오.

public class baekjoon_9375 {
    static int testCase; // 테스트 케이스
    static int n; // 해빈이가 가진 의상의 수
    static int result = 0;
    static String[][] list; // 의상의 이름, 종류가 n개만큼 들어갈 배열
    static ArrayList<String> typeList;
    static HashMap<String, Integer> map;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        // 테스트 케이스 입력받음
        testCase = Integer.parseInt(br.readLine());


        // 각 테스트 케이스 진행
        for(int i=0; i<testCase; ++i){
            n = Integer.parseInt(br.readLine());
            list = new String[n][2]; // list 메모리 부여
            result = 1;
            // 이제 의상 이름, 종류 입력받기
            for(int j=0; j<n; ++j){
                st = new StringTokenizer(br.readLine());
                list[j][0] = (String)st.nextToken(); // 의상의 이름
                list[j][1] = (String)st.nextToken(); // 의상의 종류
            }

            solution();
            System.out.println(result);
        }

        br.close();
    }

    // 현재 테스트 케이스에서 의상을 입을 수 있는 경우의 수를 얻어오는 함수
    public static void solution(){
        map = new HashMap<>();

        // hashMap을 만들되, HashMap<String, ArrayList<String>> 형태의 map을 만든다.
        // map의 key=의상 종류 / map의 value=의상 이름
        // 각 의상 조합들을 계산한다. (모두 안입는 경우만 1 빼주면 된다.)
        
        // map에 주어진 의상들을 넣는다.
        for(int i=0; i<list.length; ++i){
            String type = list[i][1]; // 의상 종류
            if(map.containsKey(type)){ // 이미 map에 있는 의상 종류면, 해당 value에 의상 개수를 더해준다.
                map.put(type, map.get(type)+1);
            } else{ // map에 없는 의상 종류면, 1값을 새로 넣어줌
                map.put(type, 1);
            }
        }

        // 분류가 완료되면, 의상 조합들을 계산한다.
        for(int value : map.values()){
            result *= (value+1); // 1을 더해서 곱해주는 것은, 안입는 것도 경우의 수에 추가해줘야 하기 때문임
            
        }
        result--; // 모두 안입는 경우를 제거
    }

}
