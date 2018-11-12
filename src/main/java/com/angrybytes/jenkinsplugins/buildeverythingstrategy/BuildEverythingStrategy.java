package com.angrybytes.jenkinsplugins.buildeverythingstrategy;

import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;
import jenkins.branch.BranchBuildStrategy;
import jenkins.branch.BranchBuildStrategyDescriptor;
import jenkins.scm.api.SCMSource;
import jenkins.scm.api.SCMHead;
import java.util.logging.Logger;
import java.util.logging.Level;
import jenkins.scm.api.mixin.TagSCMHead;

public class BuildEverythingStrategy extends BranchBuildStrategy {
    private static final Logger LOGGER = Logger.getLogger(BuildEverythingStrategy.class.getName());
    @DataBoundConstructor
    public BuildEverythingStrategy() {
    }

    @Override
    public boolean isAutomaticBuild(SCMSource source, SCMHead head) {
        if (!(head instanceof TagSCMHead)) {
            LOGGER.warning("Ignoring build because it is not tag.");
            return false;
        }

        long tagAge = System.currentTimeMillis() - ((TagSCMHead)head).getTimestamp();

        LOGGER.info("Compared System.currentTimeMillis() '" + System.currentTimeMillis() + "' to tag object '" + head.getClass() + "' timestamp '" + ((TagSCMHead)head).getTimestamp() + "' tag that has tagAge of: " + tagAge);

        return tagAge < (1000*60*5);
    }

    @Extension
    public static class DescriptorImpl extends BranchBuildStrategyDescriptor {
        public String getDisplayName() {
             return "Build recent tags";
        }
    }

}
