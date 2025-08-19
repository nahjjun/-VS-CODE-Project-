import java.util.*;

public class PCCP_석유시추 {
    static int[][] list; // 각 위치가 어떤 set인지 저장하는 리스트
    static HashMap<Integer, Integer> map; // 각 set이 얼마의 weight를 갖고 있는지 담고 있는 맵
    static final int dx[] = {0, 0, 1, -1};
    static final int dy[] = {1, -1, 0, 0};
    
    
    public int solution(int[][] land) {
        list = new int[land.length][land[0].length];
        map = new HashMap<>();
        
        int num = 1; // 집합 번호
        
        for(int i=0; i<land.length; ++i){
            for(int j=0; j<land[0].length; ++j){
                // 해당 위치가 방문하지 않은 자리며 석유가 있는 자리일 경우, 해당 위치를 시작으로 dfs로 연결된 곳을 전부 방문
                if(list[i][j] == 0 && land[i][j] == 1){ 
                    int w = dfs(i, j, num, 0, land);
                    map.put(num, w);
                    num++;
                }
            }
        }
        
        // 각 열을 돌면서 뽑을 수 있는 기름의 양을 계산한다.
        int max = 0; // 뽑을 수 있는 최대 기름 양
        for(int c=0; c<land[0].length; ++c){
            int tmp=0;
            boolean visited[] = new boolean[map.size()];
            
            for(int r=0; r<land.length; ++r){
                if(list[r][c] > 0 && !visited[list[r][c]-1]){ // 기름을 만난 경우, 해당 기름이 방문한 기름인지 확인
                    // 방문하지 않은 기름이면, 해당 기름의 양 추가 및 방문 처리
                    visited[list[r][c]-1] = true;
                    tmp += map.get(list[r][c]);
                }
            }
            max = Math.max(tmp, max);
        }
        
        return max;
    }
    
    public int dfs(int row, int col, int num, int weight, int[][] land){
        weight++;
        list[row][col] = num;
        for(int i=0; i<4; ++i){
            int nextR = row + dy[i];
            int nextC = col + dx[i];
            if(nextR<0 || nextR>=list.length || nextC<0 || nextC>=list[0].length) continue;
            if(list[nextR][nextC] == 0 && land[nextR][nextC] == 1){
                weight = dfs(nextR, nextC, num, weight, land);
            }
        }
        return weight;
    }
}