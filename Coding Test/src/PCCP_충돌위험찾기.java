import java.util.*;

public class PCCP_충돌위험찾기 {
    static int R; // 물류 센터의 행 길이
    static int C; // 물류 센터의 열 길이
    static Map<Integer, Point> pointMap = new HashMap<>();
    
    class Point {
        int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    
    public int solution(int[][] points, int[][] routes) {
        int answer = 0;
        setBoardSize(points); // 물류센터의 격자 크기를 갱신한다
        setPointMap(points); // 1~m번 포인트의 Point를 저장하고 있는 map 초기화
        
        int longestRouteDist = 0; // 가장 긴 경로의 길이를 담는 변수
        List<List<Point>> shortestRoutes = new ArrayList<>(); // 각 경로들을 담고 있는 리스트
        for (int[] r : routes) {
            List<Point> route = findShortestRoute(r); // route[0] -> route[1] 가는 최단 경로를 가져옴
            shortestRoutes.add(route); // 최단경로 list에 추가
            longestRouteDist = (longestRouteDist < route.size()) ? route.size() : longestRouteDist; // 가장 긴 경로의 길이 최신화
        }
        
        answer = findStrikeCount(shortestRoutes, longestRouteDist); // 최단경로들이 모여있는 리스트로 충돌 위험 횟수 세기
        return answer;
    }
    
    public void setBoardSize(int[][] points) {
        int r = 0;
        int c = 0;
        for (int[] point : points) {
            if (point[0] > r) {
                r = point[0];
            }
            if (point[1] > c) {
                c = point[1];
            }
        }
        R = r;
        C = c;
    }
    
    public void setPointMap(int[][] points) {
        for (int i = 0; i < points.length; i++) {
            pointMap.put(i + 1, new Point(points[i][1], points[i][0]));
        }
    }
    
    // 주어진 경로 리스트와 가장 긴 리스트 변수를 받아서 충돌하는 지점들을 return하는 함수
    public int findStrikeCount(List<List<Point>> shortestRoutes, int longestRouteDist) {
        int totalCount = 0; // 최종 충돌 횟수
        
        // 각 초당 Point별 중첩 횟수를 구한다
        for (int i = 0; i < longestRouteDist; i++) { 
            Map<Point, Integer> countMap = new HashMap<>(); // 각 Point가 중첩 횟수를 저장할 map
            
            for (List<Point> shortestRoute : shortestRoutes) {
                Point point = (shortestRoute.size() > i) ? shortestRoute.get(i) : null; // 해당 경로가 현재 초보다 작은지, 범위 안에 있는지 확인
                if (point != null) { // 해당 초에 경로가 남아있다면, 각 Point가 몇번 나오는지 갱신해준다
                    if (countMap.get(point) == null) {
                        countMap.put(point, 1);
                        continue;
                    }
                    countMap.put(point, countMap.get(point) + 1);
                }
            }
            
            // 이후, map에서 values가 2 이상인 만큼 count를 증가시킨다.
            for (Integer count : countMap.values()) {
                if (count > 1) {
                    totalCount += 1;
                }
            }
        }
        return totalCount;
    }
    
    // 행부터 이동한 후 열로 이동하여 경로를 만들어주는 함수
    public List<Point> findShortestRoute(int[] routePoints) {
        List<Point> shortestRoute = new ArrayList<>();
        for (int i = 1; i < routePoints.length; i++) {
            Point start = pointMap.get(routePoints[i - 1]);
            Point end = pointMap.get(routePoints[i]);
            int r = start.y;
            int c = start.x;
            
            if (shortestRoute.isEmpty()) {
                shortestRoute.add(new Point(r, c));
            }
            
            while (r != end.y) {
                r += (r > end.y) ? -1 : 1;
                shortestRoute.add(new Point(r, c));
            }
            
            while (c != end.x) {
                c += (c > end.x) ? -1 : 1;
                shortestRoute.add(new Point(r, c));
            }
        }
        return shortestRoute;
    }
}
