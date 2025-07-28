// <문제>
// 민식이는 수학학원에서 단어 수학 문제를 푸는 숙제를 받았다.

// 단어 수학 문제는 N개의 단어로 이루어져 있으며, 각 단어는 알파벳 대문자로만 이루어져 있다.
// 이때, 각 알파벳 대문자를 0부터 9까지의 숫자 중 하나로 바꿔서 N개의 수를 합하는 문제이다. 
// 같은 알파벳은 같은 숫자로 바꿔야 하며, 두 개 이상의 알파벳이 같은 숫자로 바뀌어지면 안 된다.

// 예를 들어, GCF + ACDEB를 계산한다고 할 때, A = 9, B = 4, C = 8, D = 6, E = 5, F = 3,
// G = 7로 결정한다면, 두 수의 합은 99437이 되어서 최대가 될 것이다.

// N개의 단어가 주어졌을 때, 그 수의 합을 최대로 만드는 프로그램을 작성하시오.

// <입력>
// 첫째 줄에 단어의 개수 N(1 ≤ N ≤ 10)이 주어진다. 둘째 줄부터 N개의 줄에 단어가 한 줄에 
// 하나씩 주어진다. 단어는 알파벳 대문자로만 이루어져있다. 모든 단어에 포함되어 있는 알파벳은 
// 최대 10개이고, 수의 최대 길이는 8이다. 서로 다른 문자는 서로 다른 숫자를 나타낸다.

// <출력>
// 첫째 줄에 주어진 단어의 합의 최댓값을 출력한다.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;




// 각 단어가 결과에 줄 수 있는 영향력을 계산한 뒤, 영향력이 높은 순서대로 숫자를 분배해서 결과를
// 계산하면 되는 문제였다.
// 아이디어 구상이 얼마나 중요한지 새삼느끼게 되는 문제였음


public class baekjoon_1339 {
    static int N;
    static String[] strings;
    // 각 알파벳 당 영향력을 저장하기 위한 map
    static Map<Character, Integer> price = new HashMap<>(); 

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        
        strings = new String[N];

        // 단어들 입력받기
        for(int i=0; i<N; ++i){
            strings[i] = br.readLine();
        }

        // 모든 알파벳 값 초기화
        for(char c = 'A'; c <= 'Z'; ++c){
            price.put((Character)c, 0);
        }

        // 모든 단어들을 순회하면서, 각 알파벳별 중요도를 계산한다.
        for(int i=0; i<N; ++i){
            // i번째 단어의 알파벳들의 중요도를 계산한다.
            for(int j=0; j<strings[i].length(); ++j){
                Character c = strings[i].charAt(j); // 검사할 알파벳 가져옴
                
                // 검사할 알파벳의 차수 계산
                int count = strings[i].length()-j-1;
                // 중요도 계산
                int tmp = (int)Math.pow(10, count);
                // 계산한 중요도를 갱신시켜줌
                price.put(c, price.get(c) + tmp);
            }
        }

        // 각 알파벳별 중요도를 계산했다면, price map의 값들을 가져와서 내림차순 정렬한다.
        ArrayList<Integer> list = new ArrayList<>(price.values());
        list.sort(Comparator.reverseOrder());

        int result = 0;
        int max = 9;
        for(int i : list){
            result += i*max--;
        }

        System.out.println(result);
    }
}
