
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
  static char[] value;
  static int gIndex;
  static int length = 0;

  enum STATE{
    state0, state1, state2, state3, state4, state5, state6;
  }

  

	 public static void main(String[] args) {

		try {
      File file = new File("input.txt");
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
        String s = scanner.nextLine();
        System.out.println(s);
        String input[] = s.split("/");
        value = input[0].trim().toCharArray();
        length = value.length;
        System.out.println(float_lit());
        System.out.println();
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    
	}


  public static boolean dec_digit(char ch){
    if((ch>='0' && ch<='9'))
      return true ;
    return false;
  }

  public static boolean dec_digits(int index){
    STATE current = STATE.state0;
    int i = index;

    while(current != STATE.state3 && i < length){
      switch(current){
        case state0:
          if(dec_digit(value[i]))
            current = STATE.state1;
          else 
            return false;
          break;
        case state1:
          if(dec_digit(value[i]))
            current = STATE.state1;
          else if(value[i] == '_')
            current = STATE.state2;
          else 
            return true;
          break;
        case state2:
          if(dec_digit(value[i]))
            current = STATE.state1;
          else if(value[i] == '_')
            current = STATE.state2;
          else 
            current = STATE.state3;
          break;
        default:
          return false;
      }
      gIndex = i;
      i++;
    }

    return current == STATE.state1;
    
  }

  public static boolean hex_digit(char ch){
    if((ch>='0' && ch<='9')||(ch>='a' && ch<='f')||(ch>='A' && ch<='F'))
      return true;
    return false;
  }

   public static boolean hex_digits(int index){
    STATE current = STATE.state0;
    int i = index;

    while(current != STATE.state3 && i < length){
      switch(current){
        case state0:
          if(hex_digit(value[i]))
            current = STATE.state1;
          else 
            return false;
          break;
        case state1:
          if(hex_digit(value[i]))
            current = STATE.state1;
          else if(value[i] == '_')
            current = STATE.state2;
          else 
            return true;
          break;
        case state2:
          if(hex_digit(value[i]))
            current = STATE.state1;
          else if(value[i] == '_')
            current = STATE.state2;
          break;
        default:
          return false;
      }
      gIndex = i;
      i++;
    }

    return current == STATE.state1;
    
  }

  public static boolean dec_exponent(int index){
    STATE current = STATE.state0;
    int i = index;

    while(current != STATE.state2 && i < length){
      switch(current){
        case state0:
          if(value[i] == 'e' || value[i] == 'E')
            current = STATE.state1;
          else 
            return false;
          break;
        case state1:
          if(value[i] == '-' || value[i] == '+')
            current = STATE.state1;
          else if (dec_digit(value[i])){
            return dec_digits(i);
          }
          else 
            return false;
          break;
        default:
          return false;
      }
      i++;
    }

    return current == STATE.state1;
  }

  public static boolean hex_exponent(int index){
    STATE current = STATE.state0;
    int i = index;

    while(current != STATE.state2 && i < length){
      switch(current){
        case state0:
          if(value[i] == 'p' || value[i] == 'P')
            current = STATE.state1;
          else 
            return false;
          break;
        case state1:
          if(value[i] == '-' || value[i] == '+')
            current = STATE.state1;
          else if (dec_digit(value[i])){
            return dec_digits(i);
          }
          else 
            return false;
          break;
        default:
          return false;
      }
      i++;
    }

    return current == STATE.state1;
  }

  public static boolean hex_mantissa(int index){
    STATE current = STATE.state0;
    int i = index;

    while(current != STATE.state5 && i < length){
      switch(current){
        case state0:
          if(value[i] == '_')
            current = STATE.state1;
          else if (value[i] == '.')
            current = STATE.state2;
          else if (hex_digit(value[i])){
            current = STATE.state2;
            i--;
          }else 
           return false;
          break;
        case state1:
          if(value[i] == '_')
            current = STATE.state1;
          else if (hex_digit(value[i])){
            current = STATE.state2;
            i--;
          }
          else 
            return false;
          break;
        case state2:
          if(hex_digits(i)){
            i = gIndex;
            current = STATE.state3;
          }
          else 
            return false;
          break;
        case state3:
          if(value[i] == '.'){
            current = STATE.state4;
          }
          else 
            current = STATE.state5;
          break;
        case state4:
          if(hex_digits(i)){
            i = gIndex;
            current = STATE.state4;
          }
          else 
            current = STATE.state5;
          break;
        default:
          return false;
      }
      i++;
    }
    gIndex = i-2;

    return current == STATE.state4 || current == STATE.state5;
  }

  public static boolean hex_float_lit (int index){
    STATE current = STATE.state0;
    int i = index;

    while(current != STATE.state4 && i < length){
      switch(current){
        case state0:
          if(value[i] == '0')
            current = STATE.state1;
          else 
            return false;
          break;
        case state1:
          if(value[i] == 'x' || value[i] == 'X')
            current = STATE.state2;
          else 
            return false;
          break;
        case state2:
          if(hex_mantissa(i)){
            i = gIndex;
            current = STATE.state3;
          }
          else 
            return false;
          break;
        case state3:
          if(hex_exponent(i)){
            i = gIndex;
            current = STATE.state4;
          }
          else 
            return false;
          break;
        default:
          return false;
      }
      i++;
    }

    if(i == length && current == STATE.state4){
      return true;
    }
    return false;
  }

  public static boolean dec_float_lit (int index){
    STATE current = STATE.state0;
    int i = index;

     while(current != STATE.state6 && i < length){
      switch(current){
        case state0:
          if(dec_digit(value[i])){
            i--;
            current = STATE.state1;
          }else if(value[i]=='.'){
            current = STATE.state5;
          }else{
            return false;
          }
          break;
        case state1:
          if(dec_digits(i)){
            i = gIndex;
            current = STATE.state2;
          }else
            return false;
          break;
        case state2:
          if(value[i]=='.'){
            current = STATE.state3;
          }else{
            i--;
            current = STATE.state4;
          }
          break;
        case state3:
          if(dec_digits(i)){
            i = gIndex;
          }else{
            i--;
          }
          current = STATE.state4;
          break;
        case state4:
          if(dec_exponent(i)){
            i = gIndex;
            System.out.println(i);
            current = STATE.state6;
          }else
            current = STATE.state6;
          break;
        case state5:
          if(dec_digits(i)){
            i = gIndex;
            current = STATE.state4;
          }else
            return false;
          break;
        default:
          return false;
      }
      i++;
     }

     if(i == length && (current == STATE.state6 || current == STATE.state4 || current == STATE.state3)){
       return true;
     }

    return false;

  }

  public static boolean float_lit(){
    return dec_float_lit(0) || hex_float_lit(0);
  }

}