package juan.settings;

public class BooleanSetting extends Setting {

	public boolean enabled;

	public BooleanSetting(String name, boolean enabled) {
		this.enabled = enabled;
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void toggle() {
		enabled = !enabled;
	}
	
}
