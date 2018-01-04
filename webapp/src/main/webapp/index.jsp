<%@ include file="includes/header.jsp" %>

<h2 class="animated bounceInDown" style="font-size:32pt; font-family:arial; color:#990000; font-weight:bold">Document Comparison Service</h2>

<!-- </p>&nbsp;</p>&nbsp;</p>
 -->
<table>
	<tr>
		<td valign="top">

			<form method="POST" enctype="multipart/form-data" action="doProcess">
				<fieldset>
					<legend style="font-weight: bold">Specify Details</legend>

					<b>Document Title :</b><br>
					<input name="txtTitle" type="text" size="50"/>
					<p/>
					<input type="file" name="txtDocument"/>
					<div style="text-align: center">
						<input type="submit" value="Compare Document"/>
					</div>			
				</fieldset>							
			</form>	

		</td>
	</tr>
</table>
<%@ include file="includes/footer.jsp" %>



