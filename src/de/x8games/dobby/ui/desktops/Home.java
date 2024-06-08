package de.x8games.dobby.ui.desktops;

import de.x8games.dobby.Helper;
import de.x8games.dobby.Program;
import de.x8games.dobby.ui.BaseDesktop;
import de.x8games.octolib3.OPiCallback;
import de.x8games.octolib3.OPiRequest;
import de.x8games.octolib3.datamodel.FileInformation;
import de.x8games.octolib3.datamodel.JobInformation;
import de.x8games.octolib3.datamodel.ProgressInformation;
import de.x8games.octolib3.requests.Job;


public class Home extends BaseDesktop<HomeUI> {

    public Home() {
        super(new HomeUI());
    }

    @Override
    public void activated() {
        Program.Printer().execute(jobCallback);
    }

    @Override
    public void deactivated() {
        jobCallback.abort();
    }

    private OPiCallback jobCallback = new OPiCallback(new Job(), 1000) {
        @Override
        public void OnSuccessful(OPiRequest request) {
            updateJobDetails((Job) request);
        }
        @Override
        public void OnFailedAsync(OPiRequest command) {
            logger.error("doof");
        }
    };

    private void updateJobDetails(Job job) {
        ui().getLblStatus().setText(job.getState());

        FileInformation fi = job.getJob().getFileInformation();
        JobInformation ji = job.getJob();
        ProgressInformation pi = job.getProgress();
        ui().getLblFilename().setText(fi.getName().orElse("nichts geladen"));
        ui().getLblFilesize().setText(Helper.Math.roundBytes(fi.getSize().orElse(0)));
        ui().getLblPrintTime().setText(Helper.Math.calcTimeFromSeconds(ji.getLastPrintTime().orElse(0.f)));
        ui().getPbarProgress().setValue(pi.getCompletion().orElse(0.f).intValue());
        ui().getLblEstimatedPrintTime().setText(Helper.Math.calcTimeFromSeconds(pi.getPrintTimeLeft().orElse(0)));
//        ui().getLblFilament().setText(ji.getFilament().);
    }

}
