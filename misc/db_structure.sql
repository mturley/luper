<!DOCTYPE html>
<html lang="en" dir="ltr">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta name="robots" content="noindex">
<title>Export: luper - Adminer</title>
<link rel="stylesheet" type="text/css" href="adminer.php?file=default.css&amp;version=3.6.3">
<script type="text/javascript" src="adminer.php?file=functions.js&amp;version=3.6.3"></script>
<link rel="shortcut icon" type="image/x-icon" href="adminer.php?file=favicon.ico&amp;version=3.6.3" id="favicon">

<body class="ltr nojs" onkeydown="bodyKeydown(event);" onclick="bodyClick(event);" onload="bodyLoad('5.5');">
<script type="text/javascript">
document.body.className = document.body.className.replace(/ nojs/, ' js');
</script>

<div id="content">
<p id="breadcrumb"><a href="adminer.php?server=localhost">MySQL</a> &raquo; <a href='adminer.php?server=localhost&amp;username=root' accesskey='1' title='Alt+Shift+1'>localhost</a> &raquo; <a href="adminer.php?server=localhost&amp;username=root&amp;db=luper">luper</a> &raquo; Export
<h2>Export: luper</h2>

<form action="" method="post">
<table cellspacing="0">
<tr><th>Output<td><label><input type='radio' name='output' value='text' checked>open</label><label><input type='radio' name='output' value='file'>save</label><label><input type='radio' name='output' value='gz'>gzip</label><label><input type='radio' name='output' value='bz2'>bzip2</label>
<tr><th>Format<td><label><input type='radio' name='format' value='sql' checked>SQL</label><label><input type='radio' name='format' value='csv'>CSV,</label><label><input type='radio' name='format' value='csv;'>CSV;</label><label><input type='radio' name='format' value='tsv'>TSV</label>
<tr><th>Database<td><select name='db_style'><option><option>USE<option selected>DROP+CREATE<option>CREATE<option>CREATE+ALTER</select><label for='checkbox-1'><input type='checkbox' name='routines' value='1' checked id='checkbox-1'>Routines</label><label for='checkbox-2'><input type='checkbox' name='events' value='1' checked id='checkbox-2'>Events</label><tr><th>Tables<td><select name='table_style'><option><option selected>DROP+CREATE<option>CREATE<option>CREATE+ALTER</select><label for='checkbox-3'><input type='checkbox' name='auto_increment' value='1' id='checkbox-3'>Auto Increment</label><label for='checkbox-4'><input type='checkbox' name='triggers' value='1' checked id='checkbox-4'>Triggers</label><tr><th>Data<td><select name='data_style'><option><option>TRUNCATE+INSERT<option selected>INSERT<option>INSERT+UPDATE</select></table>
<p><input type="submit" value="Export">

<table cellspacing="0">
<thead><tr><th style='text-align: left;'><label><input type='checkbox' id='check-tables' checked onclick='formCheck(this, /^tables\[/);'>Tables</label><th style='text-align: right;'><label>Data<input type='checkbox' id='check-data' checked onclick='formCheck(this, /^data\[/);'></label></thead>
<tr><td><label for='checkbox-5'><input type='checkbox' name='tables[]' value='Clips' checked onclick="checkboxClick(event, this); formUncheck(&#039;check-tables&#039;);" id='checkbox-5'>Clips</label><td align='right'><label>0<input type='checkbox' name='data[]' value='Clips' checked onclick="checkboxClick(event, this); formUncheck(&#039;check-data&#039;);" id='checkbox-6'></label>
<tr><td><label for='checkbox-7'><input type='checkbox' name='tables[]' value='Files' checked onclick="checkboxClick(event, this); formUncheck(&#039;check-tables&#039;);" id='checkbox-7'>Files</label><td align='right'><label>0<input type='checkbox' name='data[]' value='Files' checked onclick="checkboxClick(event, this); formUncheck(&#039;check-data&#039;);" id='checkbox-8'></label>
<tr><td><label for='checkbox-9'><input type='checkbox' name='tables[]' value='Sequences' checked onclick="checkboxClick(event, this); formUncheck(&#039;check-tables&#039;);" id='checkbox-9'>Sequences</label><td align='right'><label>0<input type='checkbox' name='data[]' value='Sequences' checked onclick="checkboxClick(event, this); formUncheck(&#039;check-data&#039;);" id='checkbox-10'></label>
<tr><td><label for='checkbox-11'><input type='checkbox' name='tables[]' value='Tracks' checked onclick="checkboxClick(event, this); formUncheck(&#039;check-tables&#039;);" id='checkbox-11'>Tracks</label><td align='right'><label>0<input type='checkbox' name='data[]' value='Tracks' checked onclick="checkboxClick(event, this); formUncheck(&#039;check-data&#039;);" id='checkbox-12'></label>
<tr><td><label for='checkbox-13'><input type='checkbox' name='tables[]' value='Users' checked onclick="checkboxClick(event, this); formUncheck(&#039;check-tables&#039;);" id='checkbox-13'>Users</label><td align='right'><label>0<input type='checkbox' name='data[]' value='Users' checked onclick="checkboxClick(event, this); formUncheck(&#039;check-data&#039;);" id='checkbox-14'></label>
</table>
</form>
</div>

<form action='' method='post'>
<div id='lang'>Language: <select name='lang' onchange="this.form.submit();"><option value="en" selected>English<option value="cs">Čeština<option value="sk">Slovenčina<option value="nl">Nederlands<option value="es">Español<option value="de">Deutsch<option value="fr">Français<option value="it">Italiano<option value="et">Eesti<option value="hu">Magyar<option value="pl">Polski<option value="ca">Català<option value="pt">Português<option value="sl">Slovenski<option value="lt">Lietuvių<option value="tr">Türkçe<option value="ro">Limba Română<option value="id">Bahasa Indonesia<option value="ru">Русский язык<option value="uk">Українська<option value="sr">Српски<option value="zh">简体中文<option value="zh-tw">繁體中文<option value="ja">日本語<option value="ta">த‌மிழ்<option value="bn">বাংলা<option value="ar">العربية<option value="fa">فارسی</select> <input type='submit' value='Use' class='hidden'>
<input type='hidden' name='token' value='473052'>
</div>
</form>
<div id="menu">
<h1>
<a href='http://www.adminer.org/' id='h1'>Adminer</a> <span class="version">3.6.3</span>
<a href="http://www.adminer.org/#download" id="version"></a>
</h1>
<form action="" method="post">
<p class="logout">
<a href='adminer.php?server=localhost&amp;username=root&amp;db=luper&amp;sql='>SQL command</a>
<a href='adminer.php?server=localhost&amp;username=root&amp;db=luper&amp;dump=' id='dump' class='active'>Dump</a>
<input type="submit" name="logout" value="Logout" id="logout">
<input type="hidden" name="token" value="473052">
</p>
</form>
<form action="">
<p id="dbs">
<input type="hidden" name="server" value="localhost"><input type="hidden" name="username" value="root"><select name='db' onchange="this.form.submit();"><option value="">(database)<option>information_schema<option selected>luper<option>mysql<option>performance_schema<option>phpmyadmin<option>test</select><input type="submit" value="Use" class='hidden'>
<input type="hidden" name="dump" value=""></p></form>
<p><a href="adminer.php?server=localhost&amp;username=root&amp;db=luper&amp;create=">Create new table</a>
<p id='tables' onmouseover='menuOver(this, event);' onmouseout='menuOut(this);'>
<a href="adminer.php?server=localhost&amp;username=root&amp;db=luper&amp;select=Clips">select</a> <a href="adminer.php?server=localhost&amp;username=root&amp;db=luper&amp;table=Clips" title='Show structure'>Clips</a><br>
<a href="adminer.php?server=localhost&amp;username=root&amp;db=luper&amp;select=Files">select</a> <a href="adminer.php?server=localhost&amp;username=root&amp;db=luper&amp;table=Files" title='Show structure'>Files</a><br>
<a href="adminer.php?server=localhost&amp;username=root&amp;db=luper&amp;select=Sequences">select</a> <a href="adminer.php?server=localhost&amp;username=root&amp;db=luper&amp;table=Sequences" title='Show structure'>Sequences</a><br>
<a href="adminer.php?server=localhost&amp;username=root&amp;db=luper&amp;select=Tracks">select</a> <a href="adminer.php?server=localhost&amp;username=root&amp;db=luper&amp;table=Tracks" title='Show structure'>Tracks</a><br>
<a href="adminer.php?server=localhost&amp;username=root&amp;db=luper&amp;select=Users">select</a> <a href="adminer.php?server=localhost&amp;username=root&amp;db=luper&amp;table=Users" title='Show structure'>Users</a><br>
<script type='text/javascript'>
var jushLinks = { sql: [ 'adminer.php?server=localhost&username=root&db=luper&table=$&', /\b(Clips|Files|Sequences|Tracks|Users)\b/g ] };
jushLinks.bac = jushLinks.sql;
jushLinks.bra = jushLinks.sql;
jushLinks.sqlite_quo = jushLinks.sql;
jushLinks.mssql_bra = jushLinks.sql;
</script>
</div>
