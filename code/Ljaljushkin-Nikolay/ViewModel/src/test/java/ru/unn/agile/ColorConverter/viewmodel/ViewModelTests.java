package ru.unn.agile.ColorConverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getFirstChannelSrcColorString());
        assertEquals("", viewModel.getSecondChannelSrcColorString());
        assertEquals("", viewModel.getThirdChannelSrcColorString());

        assertEquals("", viewModel.getFirstChannelDstColorString());
        assertEquals("", viewModel.getSecondChannelDstColorString());
        assertEquals("", viewModel.getThirdChannelDstColorString());

        assertEquals(Color.RGB, viewModel.getSrcColor());
        assertEquals(Color.LAB, viewModel.getDstColor());
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingWhenConvertWithEmptyFields() {
        viewModel.convert();
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsAreFill() {
        fillInputFields();
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsWaitingWhenNotAllFieldsAreFill() {
        fillNotAllFields();
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void canReportBadFormat() {
        fillFieldInBadFormat();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void canReportOutOfRangeForRgb() {
        setValueOutsideAcceptableRangesForRgb();
        viewModel.convert();
        assertEquals(Status.OUT_OF_RANGE.toString(), viewModel.getStatus());
    }

    @Test
    public void canReportOutOfRangeForLab() {
        viewModel.setSrcColor(Color.LAB);
        setValueOutsideAcceptableRangesForLab();
        viewModel.convert();
        assertEquals(Status.OUT_OF_RANGE.toString(), viewModel.getStatus());
    }

    @Test
    public void canReportOutOfRangeForHsv() {
        viewModel.setSrcColor(Color.HSV);
        setValueOutsideAcceptableRangesForHsv();
        viewModel.convert();
        assertEquals(Status.OUT_OF_RANGE.toString(), viewModel.getStatus());
    }

    private void fillInputFields() {
        viewModel.setFirstChannelSrcColorString("0");
        viewModel.setSecondChannelSrcColorString("0");
        viewModel.setThirdChannelSrcColorString("0");
        viewModel.setFirstChannelDstColorString("0");
        viewModel.setSecondChannelDstColorString("0");
        viewModel.setThirdChannelDstColorString("0");
    }

    private void setValueOutsideAcceptableRangesForRgb() {
        fillInputFields();
        viewModel.setFirstChannelSrcColorString("-1");
    }

    private void setValueOutsideAcceptableRangesForLab() {
        fillInputFields();
        viewModel.setSecondChannelSrcColorString("-129");
    }

    private void setValueOutsideAcceptableRangesForHsv() {
        fillInputFields();
        viewModel.setThirdChannelSrcColorString("2");
    }

    private void fillNotAllFields() {
        viewModel.setFirstChannelSrcColorString("0");
    }

    private void fillFieldInBadFormat() {
        viewModel.setFirstChannelSrcColorString("0trash$");
    }
}
