package com.juaracoding.plugin;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.juaracoding.Hooks;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;

public class ExtentCucumberPlugin implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::handleStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleStepFinished);
    }

    private void handleStepStarted(TestStepStarted event) {
        if (!(event.getTestStep() instanceof PickleStepTestStep pickleStep)) {
            return;
        }

        String keyword = pickleStep.getStep().getKeyword().trim();
        String stepText = pickleStep.getStep().getText();
        Hooks.startStepNode(keyword, stepText);
    }

    private void handleStepFinished(TestStepFinished event) {
        if (!(event.getTestStep() instanceof PickleStepTestStep)) {
            return;
        }

        ExtentTest stepNode = Hooks.getCurrentStepTest();
        if (stepNode == null) {
            return;
        }

        io.cucumber.plugin.event.Status resultStatus = event.getResult().getStatus();
        if (resultStatus == io.cucumber.plugin.event.Status.PASSED) {
            stepNode.pass("Step passed");
        } else if (resultStatus == io.cucumber.plugin.event.Status.FAILED) {
            stepNode.fail(event.getResult().getError());
        } else if (resultStatus == io.cucumber.plugin.event.Status.SKIPPED
            || resultStatus == io.cucumber.plugin.event.Status.PENDING) {
            stepNode.skip("Step " + resultStatus.name().toLowerCase());
        } else {
            stepNode.log(Status.WARNING, "Step finished with status: " + resultStatus.name());
        }

        Hooks.clearCurrentStepTest();
    }
}
