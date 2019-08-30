<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<%@ include file="parts/meta.jsp"%>
<title>Employees Page</title>
<%@ include file="parts/header.jsp"%>

</head>
<body>
	<%@ include file="parts/navbar.jsp"%>
	<main class="container">
	<div class="table-wrapper">
		<div class="table-title">
			<div class="row">
				<div class="col-sm-6">
					<h2>
						Manage <b>Employees</b>
					</h2>
				</div>
				<div class="col-sm-6">
					<a href="#addEmployeeModal" class="btn btn-success"
						data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add
							New Employee</span></a>
				</div>
			</div>
		</div>
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th>Name</th>
					<th>Department</th>
					<th>Email</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="data" items="${employees}" varStatus="status">
					<tr>
						<td>${data.firstName} ${data.lastName}</td>
						<td>${data.department.depName}</td>
						<td>${data.email}</td>
						<td>
							<a href="#editEmployeeModal" data-empId="${data.empId}" data-firstName="${data.firstName}" data-lastName="${data.lastName}" data-email="${data.email}" data-depId="${data.department.depId}" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a> 
							<a href="#deleteEmployeeModal" data-record="${data.empId}" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</main>
	<!-- Add Modal HTML -->
	<div id="addEmployeeModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="addForm">
					<div class="modal-header">
						<h4 class="modal-title">Add Employee</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label>First-Name</label> <input type="text" name="firstName"
								class="form-control" required>
						</div>
						<div class="form-group">
							<label>Last-Name</label> <input type="text" name="lastName"
								class="form-control" required>
						</div>
						<div class="form-group">
							<label>Email</label> <input type="email" name="email"
								class="form-control" required>
						</div>
						<div class="form-group">
							<label>Department</label> 
							<select class="form-control" name="department.depId" required>
								<option value="">Select Department</option>
								<c:forEach var="dep" items="${department}">
									<option value="${dep.depId}">${dep.depName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal"
							value="Cancel"> <input type="submit"
							class="btn btn-success btn-add" value="Add">
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- Edit Modal HTML -->
	<div id="editEmployeeModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="editForm">
					<div class="modal-header">
						<h4 class="modal-title">Edit Employee</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label>First-Name</label> 
							<input type="text" name="firstName"	class="form-control" required>
						</div>
						<div class="form-group">
							<label>Last-Name</label> 
							<input type="text" name="lastName" class="form-control" required>
						</div>
						<div class="form-group">
							<label>Email</label> 
							<input type="email" name="email" class="form-control" required>
						</div>
						<div class="form-group">
							<label>Department</label> 
							<select class="form-control" name="department.depId" required>
								<option value="">Select Department</option>
								<c:forEach var="dep" items="${department}">
									<option value="${dep.depId}">${dep.depName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal"
							value="Cancel"> <input type="submit"
							class="btn btn-success btn-edit" value="Edit">
					</div>
					<input type="hidden" name="empId" class="form-control">
				</form>
			</div>
		</div>
	</div>
	<!-- Delete Modal HTML -->
	<div id="deleteEmployeeModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form>
					<div class="modal-header">
						<h4 class="modal-title">Delete Employee</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">
						<p>Are you sure you want to delete these Records?</p>
						<p class="text-warning">
							<small>This action cannot be undone.</small>
						</p>
					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal"
							value="Cancel"> <input type="submit"
							class="btn btn-danger btn-delete" value="Delete">
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="parts/footer.jsp"%>
</body>
<script type="text/javascript">
	
	jQuery(function($) {
		// Modal delete function
		$('#deleteEmployeeModal').on('click', '.btn-delete', function(e) {
			e.preventDefault();
			var $modalDiv = $(e.delegateTarget);
			var id = $(this).data('record');
			console.log(id);
			$modalDiv.addClass('loading');
			$.ajax({
				type : "DELETE",
				url : '/api/v1/employees/' + id,
				success : function(data) {
					console.info(data);
				},
				error : function(data) {
					console.error('Error:', data);
				}
			}).done(function() {
				$modalDiv.modal('hide').removeClass('loading');
				location.reload();  
			});
		});

		$('#deleteEmployeeModal').on('show.bs.modal', function(e) {
			var data = $(e.relatedTarget).data();
			$('.btn-delete', this).data('record', data.record);
		});

		// Modal add function
		$('#addEmployeeModal').on('click', '.btn-add', function(e) {
			e.preventDefault();
			$.ajax({
				type : "POST",
				url : '/api/v1/employees/',
				data : $("#addForm").serialize(),
				success : function(data) {
					console.info(data);
				},
				error : function(data) {
					console.error('Error:', data);
				}
			}).done(function() {
				location.reload();  
			});
		});
		
		// Edit function
		$('#editEmployeeModal').on('click', '.btn-edit', function(e) {
			e.preventDefault();
			var $modalDiv = $(e.delegateTarget);
			var id = $(this).data('empid');
			console.log(id);
			$modalDiv.addClass('loading');
			$.ajax({
				type : "PUT",
				url : '/api/v1/employees/' + id,
				data : $("#editForm").serialize(),
				success : function(data) {
					console.info(data);
				},
				error : function(data) {
					console.error('Error:', data);
				}
			}).done(function() {
				$modalDiv.modal('hide').removeClass('loading');
				location.reload();  
			});
		});

		$('#editEmployeeModal').on('show.bs.modal', function(e) {
			var data = $(e.relatedTarget).data();
			$('.btn-edit', this).data('empid', data.empid);
			$("#editForm input[name=empId]").val(data.empid);
			$("#editForm input[name=firstName]").val(data.firstname);
			$("#editForm input[name=lastName]").val(data.lastname);
			$("#editForm input[name=email]").val(data.email);
			$('#editForm option[value=' + data.depid + ']').attr('selected','selected');
		});
	});
</script>
</html>
