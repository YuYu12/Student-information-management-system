package web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;



@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDbUtil;
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		}
		catch(Exception e) {
			throw new ServletException(e);
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			// if the command is missing, then default to listing students;
			if(theCommand == null) {
				theCommand = "LIST";
			}
			switch(theCommand) {
			case "LIST":
				listStudents(request, response);
				break;
			case "ADD" :
				addStudent(request, response);
				break;
			case "LOAD":
				loadStudent(request, response);
				break;
			case "UPDATE":
				updateStudent(request, response);
				break;
			case "DELETE":
				deleteStudent(request, response);
				break;
			default:
				listStudents(request, response);
			}
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}


	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String theStudentId = request.getParameter("studentId");
		studentDbUtil.deleteStudent(theStudentId);
		listStudents(request, response);
		
	}


	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student theStudent = new Student(id, firstName, lastName, email);
		studentDbUtil.updateStudent(theStudent);
		listStudents(request, response);
	}


	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//get the student id from request form
		String theStudentId = request.getParameter("studentId");
		//get the student from db
		Student theStudent = studentDbUtil.getStudent(theStudentId);
		//place student in the request attribute
		request.setAttribute("THE_STUDENT", theStudent);
		//send to jsp page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}


	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read student info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		//create a new student object
		Student newStudent = new Student(firstName, lastName, email);
		//add to the database
		studentDbUtil.addStudent(newStudent);
		//send back to new page
		listStudents(request, response);
		
	}


	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//get students from dbUtil;
		List<Student> students = studentDbUtil.getStudents();
		//add students to the request;
		request.setAttribute("STUDENT_LIST", students);
		//send to JSP view
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}

}
