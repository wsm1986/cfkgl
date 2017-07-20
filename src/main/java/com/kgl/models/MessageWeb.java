package  com.kgl.models;


/**
 * A message to be displayed in web context. Depending on the type, different style will be applied.
 */
public class MessageWeb {
    /**
     * Name of the flash attribute.
     */
	public static final String MESSAGE_ATTRIBUTE = "message";
	public static final MessageWeb SUCCESS_SAVE = new MessageWeb("schema.save.create", Type.SUCCESS);
	public static final MessageWeb SUCCESS_ALTER = new MessageWeb("schema.alter.sucess", Type.SUCCESS);
	public static final MessageWeb ERROR_SAVE = new MessageWeb("schema.save.error", Type.DANGER);
	public static final MessageWeb ERROR_ALTER = new MessageWeb("schema.alter.error", Type.DANGER);
	public static final MessageWeb FIND_SCHEMA_NOTFOUND = new MessageWeb("schema.find.error", Type.DANGER);

    /**
     * The type of the message to be displayed. The type is used to show message in a different style.
     */
	public static enum Type {
        DANGER, WARNING, INFO, SUCCESS;
	}

	private final String message;
	private final Type type;
	private final Object[] args;

	public MessageWeb(String message, Type type) {
		this.message = message;
		this.type = type;
		this.args = null;
	}
	
	public MessageWeb(String message, Type type, Object... args) {
		this.message = message;
		this.type = type;
		this.args = args;
	}

	public static String getMessageAttribute() {
		return MESSAGE_ATTRIBUTE;
	}

	public static MessageWeb getSuccessSave() {
		return SUCCESS_SAVE;
	}

	public static MessageWeb getSuccessAlter() {
		return SUCCESS_ALTER;
	}

	public static MessageWeb getErrorSave() {
		return ERROR_SAVE;
	}

	public static MessageWeb getErrorAlter() {
		return ERROR_ALTER;
	}

	public static MessageWeb getFindSchemaNotfound() {
		return FIND_SCHEMA_NOTFOUND;
	}

	public String getMessage() {
		return message;
	}

	public Type getType() {
		return type;
	}

	public Object[] getArgs() {
		return args;
	}
}
