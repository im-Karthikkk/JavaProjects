class B {
	interface
	public static void a(){
		try{
			Float f1 = new Float("3.0");
			int x = f1.intValue();
			byte b = f1.byteValue();
			double d = f1.doubleValue();
			System.out.println(x+b+d);
		}
		catch(NumberFormatException e){
			System.out.println("bad number");
		}
	}
	public static double b(double d){
		return Math.sqrt(d);
	}
	public static void main(String[] args){
		System.out.println(b(-9.0));
	}
}