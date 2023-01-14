class TransactionHaltException extends Exception{
	TransactionHaltException(){
		super("Either insufficient balance or beneficiary not found, please check and try again");
	}
}
class PasswordMismatchException extends Exception{
	PasswordMismatchException(){
		super("Either ID or password invalid, please try again");
	}
}
class PasswordNotChangedException extends Exception{
	PasswordNotChangedException(){
		super("No more transactions can be carried out further, please change the password immediately");
	}
}