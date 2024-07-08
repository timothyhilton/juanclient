package juan.settings;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting {

	public int index;
	public List<String> modes;
	
	public ModeSetting(String name, String defaultMode, String... modes) {
		this.name = name;
		this.modes = Arrays.asList(modes);
		index = this.modes.indexOf(defaultMode);
	}
	
	public String getMode() {
		return modes.get(index);
	}
	
	public boolean is(String mode) {
		return index == modes.indexOf(mode);
	}
	
	public void cycle(boolean positive) {
		if(positive) {
			if(index < modes.size() - 1) {
				index++;
			} else {
				index = 0;
			}
		} else {
			if(index > 0) {
				index--;
			} else {
				index = modes.size() - 1;
			}
		}
	}
	
	public List<String> getModes() {
		return modes;
	}
}
