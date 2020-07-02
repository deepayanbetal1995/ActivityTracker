package ActivityTracker.UI;

import ActivityTracker.Model.ActivityTrackerModel;
import ActivityTracker.Model.ActivityTrackerOperation;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ActivityTracker implements ActionListener {

	ActivityTrackerModel ATModel = new ActivityTrackerModel();
	ActivityTrackerOperation ATO = new ActivityTrackerOperation();
	JLabel uid, workHour, workArea, project, pendingWorkItem, ToDoTime, leftTime;
	JTextField uidField, workHourField, workAreaField, projectField, pendingWorkItemField, ToDoTimeField, leftTimeField;
	JButton b;

	public void FieldDesign() {

		JFrame f = new JFrame();

		uid = new JLabel("UID");
		uid.setBounds(10, 10, 300, 30);
		uidField = new JTextField();
		uidField.setBounds(150, 10, 100, 30);

		workHour = new JLabel("Working Hours");
		workHour.setBounds(10, 40, 300, 30);
		workHourField = new JTextField();
		workHourField.setBounds(150, 40, 100, 30);

		workArea = new JLabel("workArea");
		workArea.setBounds(10, 70, 300, 30);
		workAreaField = new JTextField();
		workAreaField.setBounds(150, 70, 100, 30);

		project = new JLabel("Project");
		project.setBounds(10, 100, 300, 30);
		projectField = new JTextField();
		projectField.setBounds(150, 100, 100, 30);

		pendingWorkItem = new JLabel("Pending Work Item");
		pendingWorkItem.setBounds(10, 130, 300, 30);
		pendingWorkItemField = new JTextField();
		pendingWorkItemField.setBounds(150, 130, 100, 30);
		
		ToDoTime = new JLabel("Allocated Time");
		ToDoTime.setBounds(10, 160, 300, 30);
		ToDoTimeField = new JTextField();
		ToDoTimeField.setBounds(150, 160, 100, 30);

		b = new JButton("click");
		b.setBounds(150, 200, 100, 40);
		b.addActionListener(this);

		f.add(b);
		f.add(uid);
		f.add(workHour);
		f.add(workArea);
		f.add(project);
		f.add(pendingWorkItem);
		f.add(ToDoTime);

		f.add(uidField);
		f.add(workHourField);
		f.add(workAreaField);
		f.add(projectField);
		f.add(pendingWorkItemField);
		f.add(ToDoTimeField);
		
		f.setSize(700, 500);
		f.setLayout(null);
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ATModel.UID = uidField.getText();
		ATModel.workingHours = Double.parseDouble(workHourField.getText());
		ATModel.workArea = workAreaField.getText();
		ATModel.Project = projectField.getText();
		ATModel.pendingWorkItem = pendingWorkItemField.getText();
		ATModel.toDoTime= Double.parseDouble(ToDoTimeField.getText());

		if (e.getSource() == b) {

			ATO.DataDisp();
			try {
				ATO.ExcelUpdate(ATModel);
			} catch (InvalidFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
