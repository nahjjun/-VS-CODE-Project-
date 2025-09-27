import java.io.*;
import java.util.*;


// <문제>
// 언제나 최고만을 지향하는 굴지의 대기업 진영 주식회사가 신규 사원 채용을 실시한다. 
// 인재 선발 시험은 1차 서류심사와 2차 면접시험으로 이루어진다. 최고만을 지향한다는 기업의 
// 이념에 따라 그들은 최고의 인재들만을 사원으로 선발하고 싶어 한다.

// 그래서 진영 주식회사는, 다른 모든 지원자와 비교했을 때 서류심사 성적과 면접시험 성적 중 
// 적어도 하나가 다른 지원자보다 떨어지지 않는 자만 선발한다는 원칙을 세웠다. 즉, 어떤 지원자
//  A의 성적이 다른 어떤 지원자 B의 성적에 비해 서류 심사 결과와 면접 성적이 모두 떨어진다면 
// A는 결코 선발되지 않는다.

// 이러한 조건을 만족시키면서, 진영 주식회사가 이번 신규 사원 채용에서 선발할 수 있는 
// 신입사원의 최대 인원수를 구하는 프로그램을 작성하시오.

// <입력>
// 첫째 줄에는 테스트 케이스의 개수 T(1 ≤ T ≤ 20)가 주어진다. 각 테스트 케이스의 첫째 줄에
//  지원자의 숫자 N(1 ≤ N ≤ 100,000)이 주어진다. 둘째 줄부터 N개 줄에는 각각의 지원자의 
// 서류심사 성적, 면접 성적의 순위가 공백을 사이에 두고 한 줄에 주어진다. 두 성적 순위는 모두
// 1위부터 N위까지 동석차 없이 결정된다고 가정한다.

// <출력>
// 각 테스트 케이스에 대해서 진영 주식회사가 선발할 수 있는 신입사원의 최대 인원수를 한 줄에 하나씩 출력한다.



// -> 문제 내용 자체가 이해하기 어려운 문제
// 한 사람의 순위 결과를 다른 모든 사람들이랑 비교했을 때, 단 한명이라도 순위 조건(적어도 하나는 우수
// 해야함)에 안맞는다면, 해당 인원은 뽑지 않는다.

public class baekjoon_1946 {
    public static class Score{
        public int one;
        public int two;
        public Score(int o, int t){
            one = o;
            two = t;
        }
    }

    static int T; // 테스트 케이스 개수
    static int N; // 지원자 숫자
    static Score list[]; // 각 테스트케이스별 리스트

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 테스트 케이스 입력
        T = Integer.parseInt(br.readLine());

        int result;

        for(int i=0; i<T; ++i){
            N = Integer.parseInt(br.readLine());
            list = new Score[N];
            result = 1;
            

            // 테스트 케이스 list 입력받기
            for(int j=0; j<N; ++j){
                st = new StringTokenizer(br.readLine());
                int one = Integer.parseInt(st.nextToken());
                int two = Integer.parseInt(st.nextToken());

                list[j] = new Score(one, two);
            }

            // 1차 결과를 기준으로 오름차순 정렬한 뒤, 2차 결과는 자신보다 앞의 인덱스와
            // 만 비교하면 된다. (본인이 더 순위가 높은 경우 result+1)

            // one의 값을 기준으로 오름차순
            Arrays.sort(list, (s1, s2)->s1.one-s2.one);

            // 지금까지 나온 등수 중 가장 높은 등수(값이 작은 것)를 저장하는 변수. 해당 등수
            // 보다 더 낮은 등수라면 기록하지 않고, 해당 등수보다 높은 등수만 기록한다.
            // 이렇게 되면, 자연스럽게 본인보다 앞의 인덱스만 검사하게 되는 것이다.
            int min = list[0].two;
            for(int j=1; j<N; ++j){
                // 만약 더 높은 등수가 나온다면, result를 증가시키고 min 최신화
                if(list[j].two < min){ 
                    min = list[j].two;
                    ++result;
                }
                
            }
            System.out.println(result);
        }
        br.close();
    }


}
