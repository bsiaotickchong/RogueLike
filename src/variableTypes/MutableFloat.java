package variableTypes;

public class MutableFloat {
	float value;
	
	public MutableFloat (float value){
		this.value = value;
	}
	
	public final float getValue(){
		return value;
	}
	
	public final void setValue(float newValue){
		value = newValue;
	}
	
	public final void add(float increase){
		value += increase;
	}
}
