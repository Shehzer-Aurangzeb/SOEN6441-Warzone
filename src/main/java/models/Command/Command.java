package models.Command;

public class Command {
    private String d_name;
    private String d_description;
    private String[] d_commandArgs;

    /**
     * Constructs a Command object with the specified name, description, and arguments.
     * @param new_name The name of the command.
     * @param new_description The description of the command.
     * @param new_commandArgs The arguments of the command.
     */
    public Command(String new_name,String new_description,String[] new_commandArgs){
        this.d_name= new_name;
        this.d_description= new_description;
        this.d_commandArgs= new_commandArgs;

    }
    /**
     * Constructs a Command object with the specified name and description.
     * @param new_name The name of the command.
     * @param new_description The description of the command.
     */
    public Command(String new_name,String new_description){
        this.d_name= new_name;
        this.d_description= new_description;
    }
    public String getName(){
        return this.d_name;
    }
    /**
     * Returns a string representation of the command, including its name, description, and arguments (if any).
     * @return A string representation of the command.
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        if(this.d_commandArgs==null) stringBuilder.append("-").append(this.d_name).append(": ").append(this.d_description);
        else{
            stringBuilder.append("-").append(this.d_name).append(" ");
            for(String command: this.d_commandArgs){
                stringBuilder.append('<').append(command).append('>').append(" ");
            }
            stringBuilder.append(": ").append(this.d_description);

        }
        return stringBuilder.toString();
    }

}
