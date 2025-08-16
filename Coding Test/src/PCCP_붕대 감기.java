import java.util.*;


class Solution {
    public int solution(int[] bandage, int health, int[][] attacks) {
        int t = bandage[0]; // 시전 시간
        int x = bandage[1]; // 초당 회복량
        int y = bandage[2]; // 스킬 시전 완료 시, 추가 회복량
        
        int currentT = 0; // 현재 시간
        int currentH = health; // 현재 체력
        
        for(int i=0; i<attacks.length; ++i){
            int attackT = attacks[i][0];
            int attackH = attacks[i][1];
            
            // currentT가 attack 시간보다 적은경우
            if(currentT < attackT){
                // 현재 체력이 최댓값이 아닌 경우, 일정 수준만큼 체력을 회복시켜야함
                if(currentH < health){
                    int remainT = attackT - currentT; // 현재 해결해야하는 시간

                    currentH += remainT*x + (remainT/t)*y;
                    if(currentH > health) currentH = health; // 최대 체력 초과하지 않게 설정
                }
            }
            
            // 지나와서 attack 시간이 되면, 현재 시간을 attackT + 1로 설정, 체력 깎기
            currentT = attackT + 1;
            currentH -= attackH;
            if(currentH <= 0) {
                return -1;
            }
        }
        
        return currentH;
    }
}