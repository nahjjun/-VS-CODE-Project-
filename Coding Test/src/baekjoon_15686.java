
// 도시는 N x N 형태의 크기이다
// 도시의 각 칸은 
    // 1. 빈칸 (0)
    // 2. 집 (1)
    // 3. 치킨집 (2)
    // 중 하나이다.

// 치킨 거리 : 집 <-> 가장 가까운 치킨집 사이의 거리
// 도시의 치킨 거리 : 모든 집의 치킨 거리의 합

// 두 칸 (r1, c1)과 (r2, c2) 사이의 거리 :  |r1-r2| + |c1-c2|

// 현재 주어진 도시의 정보에서, M개의 치킨집만 남기고 도시의 치킨 거리가 가장 작아질 수 있는
// 경우의 수를 찾아야한다. 이때의 치킨 거리를 return한다.

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class baekjoon_15686 {
    static int N, M;
    static int[][] city;
    static ArrayList<Point> stores; // 치킨집 좌표를 저장하는 배열
    static ArrayList<HashSet<Point>> sets; // 

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        stores = new ArrayList<>();
        city = new int[N][N];
        
        for(int i=0; i<N; ++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; ++j){
                city[i][j] = Integer.parseInt(st.nextToken());
                // 현재 도시에 있는 치킨집 좌표 추가
                if(city[i][j] == 2){
                    stores.add(new Point(j,i));
                }
            }
        }

        // 해당 stores 배열에서, M개의 가게 조합들의 리스트 생성
        sets = new ArrayList<>();
        HashSet<Point> s = new HashSet<>();
        initSets(s, 0);
        
        // 각 set에서 도시 치킨 거리 구하고, 그 중 min인 값 갱신
        int result = Integer.MAX_VALUE;
        for(int i=0; i<sets.size(); ++i){
            result = Math.min(result, solution(sets.get(i)));
        }

        System.out.println(result);
        br.close();
    }

    // 
    public static void initSets(HashSet<Point> set, int start){
        if(set.size() == M){
            sets.add((HashSet<Point>)set.clone());
            return;
        }

        for(int i=start; i<stores.size(); ++i){
            set.add(stores.get(i));
            initSets(set, i+1);
            set.remove(stores.get(i));
        }
    }

    // 해당 set을 사용했을 때, 해당 도시의 치킨 거리를 반환하는 함수
    public static int solution(HashSet<Point> set){
        // 1. 각 치킨집에서 각각의 집까지의 거리를 distance 배열에 저장하도록 설정
        int[][] distance = new int[N][N]; // 최소 거리를 담고 있을 배열
        for(Point p : set){
            for(int i=0; i<N; ++i){
                for(int j=0; j<N; ++j){
                    if(city[i][j] == 1){
                        int d = Math.abs((int)p.getX() - j) + Math.abs((int)p.getY() - i);
                        // distance가 0이면 아직 한번도 방문 안한 곳이므로, 그냥 값을 채워준다.
                        if(distance[i][j] == 0){ 
                            distance[i][j] = d;
                        } else { // 그렇지 않으면 기존 값과 현재 계산한 거리 중 최소값을 저장한다.
                            distance[i][j] = Math.min(d, distance[i][j]);
                        }
                    }
                }
            }
        }
        int result = 0;
        for(int i=0; i<N; ++i){
            for(int j=0; j<N; ++j){
                result += distance[i][j];
            }
        }
        return result;
    }

}
