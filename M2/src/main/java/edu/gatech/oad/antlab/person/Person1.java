package edu.gatech.oad.antlab.person;

/**
 *  A simple class for person 1
 *  returns their name and a
 *  modified string 
 *  
 *  @author Bob
 *  @version 1.1
 */
public class Person1 {
  /** Holds the persons real name */
  private String name;
  	/**
	 * The constructor, takes in the persons
	 * name
	 * @param pname the person's real name
	 */
  public Person1(String pname) {
    name = pname;
  }
  	/**
	 * This method should take the string
	 * input and return its characters rotated
	 * 2 positions.
	 * given "gtg123b" it should return
	 * "g123bgt".
	 *
	 * @param input the string to be modified
	 * @return the modified string
	 */
	private String calc(String input) {
	  //Person 1 put your implementation here
      String rotate = name;
      char[] rotation = new char[name.length()];

      for (int i = 0; i < rotation.length; i++) {
          rotation[i] = rotate.charAt(i);
      }
      char[] holder = rotation;
      for (int i = 2; i < rotation.length; i++) {
          holder[i - 2] = rotation[i];
      }
      holder[rotation.length - 2] = rotation[0];
      holder[rotation.length - 1] = rotation[1];
      rotate = "";
      for (int i = 0; i < holder.length; i++) {
          rotate = rotate + holder[i];
      }
      return rotate;
	}
	
	/**
	 * Return a string rep of this object
	 * that varies with an input string
	 *
	 * @param input the varying string
	 * @return the string representing the 
	 *         object
	 */
	public String toString(String input) {
	  return name + calc(input);
	}

}
