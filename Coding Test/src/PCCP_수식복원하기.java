import java.util.*;
import java.lang.*;
    
public class PCCP_수식복원하기 {
    // 한개의 수학 수식을 나타내기 위한 클래스
    public class Formula{
        public int v1, v2; // 두개의 변수
        public String operator; // 연산자
        public String result; // 계산 결과
        
        public Formula(int v1, int v2, String o, String r){
            this.v1 = v1;
            this.v2 = v2;
            operator = o;
            result = r;
        }
    }
    
    static Formula[] formulas; // 각 식이 들어있는 리스트
    static HashMap<Integer, ArrayList<Integer>> map; // <n번째 식의 x값의 "n", n번째 식의 x값이 될 수 있는 숫자 리스트>
    static ArrayList<Integer> existX; // X가 존재하는 행 인덱스 저장 리스트
    
    
    public String[] solution(String[] expressions) {
        formulas = new Formula[expressions.length];
        map = new HashMap<>();
        existX = new ArrayList<Integer>();
        
        // 1. expressions를 분리해서 Formula로 변환
        int i=0;
        for(String expression : expressions){
            String[] splited = expression.split(" ");
            int v1 = Integer.parseInt(splited[0]);
            int v2 = Integer.parseInt(splited[2]);
            formulas[i++] = new Formula(v1, v2, splited[1], splited[4]);
        }
        
        // 2. v1, v2, result 중 값이 가장 큰 것을 구한다.
        // 가장 큰 자릿수를 구해야함
        int biggest = 0;
        i=0;
        for(Formula f : formulas){
            biggest = Math.max(biggest, getBiggestNum(f.v1));
            biggest = Math.max(biggest, getBiggestNum(f.v2));
            
            // 만약 해당 formula의 result가 "X" 이면 패스
            if(f.result.equals("X")) {
                existX.add(i++);
                continue;
            }
            biggest = Math.max(biggest, getBiggestNum(Integer.parseInt(f.result)));
            i++;
        }
        
        // 3. biggest+1 ~ 9까지가 가능한 진수법이다.
        // 위 범위에서 각 진수로 식 계산을 수행한다. 
        for(int num=Math.max(2, biggest+1); num<=9; ++num){
            // result값이 X가 아닌 formula의 계산 결과가 맞다면, result값이 X인 formula의 결과값을 계산하여 map의 value에 결과값을 추가해준다
            if(possible(num)){                
                // formulas에서 result값이 "X"인 formula를 찾는다.
                // 해당 식에서 계산한 값을 map에 "i key"에 value에 add해준다.
                for(i=0; i<formulas.length; ++i){
                    if(formulas[i].result.equals("X")){
                        // 처음 넣는 경우, new 해준다
                        if(!map.containsKey(i)){
                            map.put(i, new ArrayList<Integer>());
                        }
                        int tmp = calculate(num, formulas[i]);
                        map.get(i).add(tmp);
                    }
                }
            }
        }
        
        // 4. 이제, map을 돌면서 value 리스트에 있는 값이 여러개인지, 혹은 모두 같은 값인지 확인한다.
        // 값이 여러개면 해당 자리에 있는 X를 ?로 채우고, 모두 같은 값이라면 해당 자리를 해당 값으로 채운다
        for(int key : existX){
            ArrayList<Integer> value = map.get(key);
            // value가 없는 경우도 "?" 처리를 해준다
            if(value == null || value.isEmpty()){
                formulas[key].result = "?";
            }
            else {
                int first = value.get(0);
                boolean allSame = true;

                // 해당 value에 들어있는 값이 모두 같은지 확인한다.
                for(i=1; i<value.size(); ++i){
                    if(first != value.get(i)){
                        allSame = false;
                        break;
                    }
                }

                // 모두 같은 경우, 해당 key번째(인덱스임) formula의 result를 해당 first값으로 채운다
                if(allSame){
                    formulas[key].result = Integer.toString(first);
                } else { // 모두 같지 않은 경우, 해당 formula의 result를 ?로 채운다
                    formulas[key].result = "?";
                }
            }
        }
        
        // 5. formulas를 String 배열로 변환한다.
        // 이때, X가 설정된 문자열만 return한다
        ArrayList<String> newString = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        
        // stringbuilder를 사용하여 String 배열로 변환
        for(int e : existX){
            Formula f = formulas[e];
            
            sb.setLength(0); // sb 비우기
            sb.append(Integer.toString(f.v1));
            sb.append(" " + f.operator + " ");
            sb.append(Integer.toString(f.v2));
            sb.append(" = " + f.result);
            
            newString.add(sb.toString());
        }
        
        return newString.toArray(new String[0]);
    }
    
    // 주어진 진수 숫자와 formulas의 식을 계산했을 때, result 값이 X가 아닌 formula들의 계산 결과가 맞는지 확인하는 함수
    public boolean possible(int num){
        for(Formula f : formulas){
            if(f.result.equals("X")) continue;
            int calculated = calculate(num, f); // 해당 formula를 계산한 값을 받아온다
            // 계산한 값이 해당 formula의 result값과 같은지 확인한다.
            // 만약 두 값이 같지 않다면, 해당 num은 possibleNumList에 추가하지 않도록 false를 return한다
            if(Integer.parseInt(f.result) != calculated){
                return false;
            }
        }
        return true;
    }
    
    // 진수와 Formula 객체를 주면 해당 결과값을 계산해주는 함수
    public int calculate(int num, Formula f){
        // num 진수값을 10진수로 바꾼다.
        int v1 = nToTen(num, f.v1);
        int v2 = nToTen(num, f.v2);
        int result = 0; // 계산 결과
        
        // 10진수로 바꾼 값을 기준으로 해당 연산 진행
        if(f.operator.equals("+")){
            result = v1+v2;
        } else if(f.operator.equals("-")){
            result = v1-v2;
        }
        
        // 10진수로 계산한 값을 num 진수로 바꾼다.
        result = tenToN(num, result);
        return result;
    }
    
    // n -> 10, 10-> n진수 변환 로직을 바꾸니 해결됐다. 뭐가 문제였을까?
    // Math.pow를 사용해서 10^n의 n을 올리면 double 형이 반환되므로, 나누기 연산을 할 때 0.0이 되는 시점까지 오래걸리므로 무한루프처럼된다
    
    // num진수 v값을 10진수로 바꿔주는 함수
    public Integer nToTen(int num, int v){
        if (v == 0) return 0;
        long res = 0;
        long mul = 1; // 각 자리수별 num^n 값 역할
        int x = v;

        while (x > 0) {
            int d = x % 10;        // 자리숫자(0~9) - 가장 첫번째 자리하나 가져와서 계산
            if (d >= num) return null; // 이 진법에선 불가
            res += d * mul;
            if (res > Integer.MAX_VALUE) return null; // 안전장치
            mul *= num;
            x /= 10;
        }
        return (int) res;
    }
    
    // 10진수 v값을 num진수로 바꿔주는 함수
    // v값을 num으로 나누면서 나온 나머지를 첫번째 자리수부터 채우면 됨 (v값이 0이 될때까지 나눈다)
    public int tenToN(int num, int v){
        if (v == 0) return 0;
        int result = 0;
        int place = 1;
        int x = v;

        while (x > 0) {
            int d = x % num;
            result += d * place;   
            x /= num;
            place *= 10;
        }
        return result;
    }
    
    
    // 해당 숫자 int에서 가장 큰 자릿수를 구하는 함수
    public int getBiggestNum(int v){
        if (v == 0) 
            return 0;
        int md = 0, t = v;
        
        while (t > 0) { 
            md = Math.max(md, t % 10); 
            t /= 10; 
        }
        return md;
    }
    
    
}