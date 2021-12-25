<h1> E-Smart </h1>
<h2>Integrated Energy Monitoring Platform for Households </h2>

<h3>Requirements & Modules</h3>
<p>This is a distributed system for an energy distribution operator that stores energy consumption data for its clients.</p>
<h4>Modules:</h4>
<ul>
  <li><strong>Energy Platform</strong>: a web app for system management and data visualization</li>
  <li><strong>Sensor Monitoring System and Real-Time Notification</strong>: a monitoring system for sensor data acquisition build with message-oriented-middleware and web sockets for asynchronous notification</li>
  <li><strong>Smart Device Notification</strong>: a part of the web app which simulates a smart device for getting data about baseline energy consumption of the client</li>
</ul>

<h3>Functional requirements</h3>
<ul>
  <li>users login and are redirected to the page corresponding to their role</li>
  <li>the users corresonding to one role will not be able to enter the pages corresponding to oher roles</li>
  <li>admin role:
     <ul>
         <li>CRUD operations for clients, devices, sensors</li>
         <li>Create mapping client-device and associate sensors to devices</li>
    </ul>
  </li>
  <li>client role:
     <ul>
       <li>view on his/her page all the devices and sensors</li>
       <li>view their monitored energy consumption</li>
       <li>view historical energy consumption on a chart</li>
    </ul>
  </li>
  <li>the message-oriented middleware allows the sensor system to send data tuples in a JSON format</li>
  <li>the message consumer component of the system processes each message and notifies asynchronously using WebSockets the client application</li>
  <li>the client application displays a chart with the client historical energy consumption over d days in the past (default d = 7)</li>
  <li>the client application displays the client baseline as a reference consumption for the next day</li>
  <li>the client application asks the server for the best start time in the next day to minimize
the peaks of energy consumption; it displays the new chart of estimated consumption as
the baseline summed with the device max consumption</li>
</ul>

<h3>Technologies</h3>
<ul>
  <li><strong>ReactJS</strong> for client development</li>
  <li><strong>Material UI</strong> and <strong>Sass</strong> for client styling</li>
  <li><strong>Spring Framework</strong> for server development</li>
  <li><strong>PostgreSQL</strong> as database management system</li>
  <li><strong>Java Swing</strong> for simulator development</li>
  <li><strong>RabbitMQ</strong> as message broker</li>
  <li><strong>Docker</strong> and <strong>Heroku</strong> for deployment</li>
</ul>
