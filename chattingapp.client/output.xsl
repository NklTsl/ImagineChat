<?xml version = "1.0" encoding = "UTF-8"?>
<xsl:stylesheet version = "1.0" 
xmlns:xsl = "http://www.w3.org/1999/XSL/Transform">   
   <xsl:template match = "/"> 
   <style>

td, th {
  border: 1px solid #ddd;
  padding: 8px;
}

tr:nth-child(even){background-color: #f2f2f2;}

tr:hover {background-color: #ddd;}

th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #4CAF50;
  color: white;
}
</style>
      <html> 
         <body> 
		 
			
            <h2>messages from <xsl:value-of select = "ChatSession/@from"/> to <xsl:value-of select = "ChatSession/@to"/> </h2>
			
			
            <table border = "1"> 
               <tr bgcolor = "#9acd32"> 
                  <th>from</th>  
                  <th>date</th> 
                  <th>time</th> 
                  <th>message</th> 
               </tr> 
				
				
               <xsl:for-each select="ChatSession/Msg"> 
                  <tr> 
					 <td><xsl:value-of select = "@from"/></td> 
                     <td><xsl:value-of select = "Date"/></td> 
                     <td><xsl:value-of select = "Time"/></td> 
                     <td><xsl:value-of select = "body" disable-output-escaping="yes"/></td> 
						
                  </tr> 
               </xsl:for-each> 
            </table> 
         </body> 
      </html> 
   </xsl:template>  
</xsl:stylesheet>