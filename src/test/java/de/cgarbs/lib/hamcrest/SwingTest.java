package de.cgarbs.lib.hamcrest;

import static de.cgarbs.lib.hamcrest.Swing.elementInside;
import static de.cgarbs.lib.hamcrest.Swing.hasBorderTitle;
import static de.cgarbs.lib.hamcrest.Swing.hasLabel;
import static de.cgarbs.lib.hamcrest.Swing.hasValue;
import static de.cgarbs.lib.hamcrest.Swing.sameImageAs;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertThat;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;

public class SwingTest
{
	static final Icon ICON_1    = UIManager.getIcon("FileChooser.homeFolderIcon");
	static final Icon ICON_2    = UIManager.getIcon("FileView.fileIcon");
	static final Icon ICON_NULL = null;

	static final String LABEL_1    = "some label";
	static final String LABEL_2    = "some other label";
	static final String LABEL_NULL = null;

	static final String TITLE_1    = "some title";
	static final String TITLE_2    = "some other title";
	static final String TITLE_NULL = null;

	@Test
	public void checkInnerElements()
	{
		JPanel thePanel = new JPanel();
		GridBagLayout gbl= new GridBagLayout();
		thePanel.setLayout(gbl);

		Component object1 = new JPanel();
		Component object2 = new JTextField();

		GridBagConstraints gbc;

		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 4;
		thePanel.add(object1, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 1;
		thePanel.add(object2, gbc);

		assertThat(elementInside(thePanel).at(3, 4), is(equalTo(object1)));

		assertThat(elementInside(thePanel).at(2, 1), is(equalTo(object2)));

		assertThat(elementInside(thePanel).at(4, 4), is(not(equalTo(object1))));
		assertThat(elementInside(thePanel).at(3, 3), is(not(equalTo(object1))));
		assertThat(elementInside(thePanel).at(3, 3), is(not(equalTo(object1))));

		assertThat(elementInside(thePanel).at(3, 4), is(not(equalTo(object2))));
		assertThat(elementInside(thePanel).at(2, 1), is(not(equalTo(object1))));

		assertThat(elementInside(thePanel).at(0,0), is(not(equalTo(object1))));
		assertThat(elementInside(thePanel).at(0,0), is(not(equalTo(object2))));
		assertThat(elementInside(thePanel).at(0,0), is(nullValue()));

		// no GridBag layout
		assertThat(elementInside(new JPanel()).at(0, 0), is(nullValue()));
	}

	@Test
	public void checkHasLabel()
	{
		final JLabel label = new JLabel();
		label.setText(LABEL_1);

		// normal operation
		assertThat(new JLabel(LABEL_1), hasLabel(equalTo(LABEL_1)));
		assertThat(label, hasLabel(equalTo(LABEL_1)));
		assertThat(new JLabel(LABEL_1), hasLabel(not(equalTo(LABEL_2))));

		// null
		assertThat(new JLabel(LABEL_NULL), hasLabel(equalTo(LABEL_NULL)));

		// default value
		assertThat(new JLabel(), hasLabel(equalTo("")));

		// wrong types
		assertThat(new JTextField(LABEL_1), hasLabel(not(equalTo(LABEL_1))));
		assertThat(new JPanel(), hasLabel(not(equalTo(""))));
	}

	@Test
	public void checkHasValue()
	{
		final String VALUE1 = "some value";
		final String VALUE2 = "some other value";
		final String NULL  = null;

		final JTextField textField = new JTextField();
		textField.setText(VALUE1);

		// normal operation
		assertThat(new JLabel(VALUE1), hasValue(equalTo(VALUE1)));
		assertThat(new JTextField(VALUE1), hasValue(equalTo(VALUE1)));
		assertThat(textField, hasValue(equalTo(VALUE1)));
		assertThat(new JLabel(VALUE1), hasValue(not(equalTo(VALUE2))));
		assertThat(new JTextField(VALUE1), hasValue(not(equalTo(VALUE2))));

		// null
		assertThat(new JLabel(NULL), hasValue(equalTo(NULL)));
		assertThat(new JTextField(NULL), hasValue(equalTo(""))); // JTextField remaps null to ""!

		// default value
		assertThat(new JLabel(), hasValue(equalTo("")));
		assertThat(new JTextField(), hasValue(equalTo("")));

		// wrong types
		assertThat(new JPanel(), hasValue(not(equalTo(""))));
		assertThat(new JSlider(), hasValue(not(equalTo(""))));
	}

	@Test
	public void checkHasBorderTitle()
	{
		final JPanel panel = new JPanel();
		assertThat(panel, hasBorderTitle(equalTo(TITLE_NULL)));

		panel.setBorder(null);
		assertThat(panel, hasBorderTitle(equalTo(TITLE_NULL)));

		panel.setBorder(new EtchedBorder());
		assertThat(panel, hasBorderTitle(not(equalTo(TITLE_NULL))));

		panel.setBorder(new TitledBorder(TITLE_1));
		assertThat(panel, hasBorderTitle(equalTo(TITLE_1)));
		assertThat(panel, hasBorderTitle(not(equalTo(TITLE_2))));

		panel.setBorder(new TitledBorder(TITLE_2));
		assertThat(panel, hasBorderTitle(equalTo(TITLE_2)));
		assertThat(panel, hasBorderTitle(not(equalTo(TITLE_1))));

		// wrong type
		assertThat(new JLabel(), hasBorderTitle(not(equalTo(TITLE_NULL))));
	}

	@Test
	public void checkSameImageAs()
	{
		// equal
		assertThat(ICON_1, is(sameImageAs(ICON_1)));
		assertThat(ICON_2, is(sameImageAs(ICON_2)));

		assertThat(ICON_NULL, is(sameImageAs(ICON_NULL)));

		// not equal
		assertThat(ICON_1, is(not(sameImageAs(ICON_2))));
		assertThat(ICON_1, is(not(sameImageAs(ICON_NULL))));

		assertThat(ICON_NULL, is(not(sameImageAs(ICON_2))));

	}

	@Test
	public void checkSameImageAsDescriptions()
	{
		Description description;

		description = new StringDescription();
		sameImageAs(ICON_1).describeTo(description);
		assertThat(
				description.toString(),
				is(not(isEmptyString()))
				);
		assertThat(
				description.toString(),
				endsWith(ICON_1.toString()+">")
				);

		description = new StringDescription();
		sameImageAs(ICON_1).describeMismatch(ICON_2, description);
		assertThat(
				description.toString(),
				is(not(isEmptyString()))
				);
		assertThat(
				description.toString(),
				endsWith(ICON_2.toString()+">")
				);
	}
}
