package sc.iview.cli;

import graphics.scenery.SceneryBase;
import net.imagej.ImageJ;
import org.scijava.script.ScriptService;
import picocli.CommandLine;
import sc.iview.SciView;

import java.io.File;
import java.util.concurrent.Callable;

public class Main implements Callable<Void> {

    @CommandLine.Option(names = {"-s", "--script"}, required = false, description = "path to a scijava script")
	private String scriptPath = null;

    @CommandLine.Option(names = {"-i", "--use-stdin"}, required = false, description = "read from stdin?")
	private boolean useStdin = false;

    @CommandLine.Option(names = {"-h", "--headless"}, required = false, description = "disable graphics")
	private boolean headless = false;

	static String[] testArgs =
			new String[]{
					"--script", "/home/kharrington/git/sciview-cli/test_script.py",
					"--use-stdin"
			};

	public static final void main(String... args) {
		//args = testArgs;

		CommandLine.call(new Main(), args);
    }

	@Override
	public Void call() throws Exception {
		// display sciview version and stuff
		System.out.println("Script path:" + scriptPath);

		// headless
		if( headless ) {
			System.setProperty("scenery.Headless", "true");
		} else {
			SceneryBase.xinitThreads();
		}

		// make imagej instance
		ImageJ imagej = new ImageJ();

		// initialize sciview
		//SciView sv = SciView.createSciView();

		// run script
		if( scriptPath != null )
			imagej.context().service(ScriptService.class).run(new File(scriptPath), true, new Object[]{});

		return null;
	}
}
