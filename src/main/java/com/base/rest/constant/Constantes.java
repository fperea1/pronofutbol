package com.base.rest.constant;

public final class Constantes {

	private Constantes() {}
	
    // mapeo peticiones controller
	
	public static final String AUTENTICATION = "autenticacion";
	
	public static final String GENERAR_TOKEN = "generate-token";
	
	public static final String LOGOUT = "logout";
	
	public static final String CONTACTO = "contacto";
	
	public static final String CONFIG = "/configuracion";
	
	public static final String USUARIOS = "/usuarios";
	
	public static final String ROLES = "/roles";
	
	public static final String LOGS = "/logs";
	
	public static final String FIND_ALL = "/findAll";
	
	public static final String FIND = "/find";
	
	public static final String FIND_BY_FILTER = "/findByFilter";
	
	public static final String SAVE = "/save";
	
	public static final String UPDATE = "/update";
	
	public static final String DELETE = "/delete";
	
	public static final String DEACTIVATE = "/deactivate";
	
	public static final String ACTIVATE = "/activate";
	
	public static final String CAMBIO_PASSWORD = "/cambioPassword";
	
	public static final String CAMBIO_PASSWORD_ADMIN = "/cambioPasswordAdmin";
	
	public static final String ENVIO_CONSULTA = "envioConsulta";
	
	public static final String GET_REPORT_EXCEL = "/getReportExcel";
	
	// Funcionalidades
	
	public static final String CONFIGURACION = "Configuración";
	
	public static final String USUARIO = "Usuario";
	
	// Acciones Log
	
	public static final String LOGIN = "Login";
	
	public static final String LOGOUT_LOG = "Logout";
	
	public static final String ALTA = "Alta";
	
	public static final String EDICION = "Edición";
	
	public static final String BAJA = "Baja";
	
	public static final String ACTIVAR = "Activar";
	
	public static final String DESACTIVAR = "Desactivar";
	
	public static final String CAMBIO_PASS_USUARIO = "Cambiar Password por Usuario";
	
	public static final String CAMBIO_PASS_ADMIN = "Cambio Password de Usuario por Administrador";
	
	// Resultados operacion
	
	public static final String OPERACION_CORRECTA = "Operación correcta";
	
	// Mensajes excepciones
	
	public static final String EXC_INTEGRIDAD_DATOS = "Excepción por infracción de integridad de datos";
	
	public static final String EXC_FALTA_PARAM_HEADER = "Falta parametro en Header";
	
	public static final String EXC_METODO_NO_SOPORTADO = "Método no soportado";
	
	public static final String EXC_AUTH_ERRONEA = "Autenticación erronea";
	
	public static final String EXC_FALTAN_PARAM_PETICION = "Faltan parámetros en la petición";
	
	public static final String EXC_LIMITE_CARACTERES_PASSWORD = "Password debe tener entre 10 y 100 caracteres";
	
	public static final String EXC_PASSWORDS_DIFERENTES = "La nueva contraseña y su confirmación no es coinciden";
	
	public static final String EXC_PASSWORD_ANT_ERRONEA = "La contraseña anterior no es correcta";
	
	public static final String EXC_NO_EXISTE_ENTIDAD = "No existe la entidad";
	
	public static final String EXC_CREDENCIALES_ERRONEAS = "Credenciales erroneas";
	
	public static final String EXC_JSON_PARSE = "Error en mapeo de objeto json";
	
	public static final String EXC_REPORT_POI = "Error al crear report xlsx";
	
	// Símbolos
	
	public static final String SEPARADOR_DOS_PUNTOS = ": ";
	
	// EXCEL
	
	public static final int MAX_ROWS_XLSX = 1048576;
	
	// MATCH MODE PARA BUSQUEDAS
	
	public static final String STARTS_WITH = "startsWith";
	
	public static final String ENDS_WITH = "endsWith";
	
	public static final String CONTAINS = "contains";
	
	public static final String NOT_CONTAINS = "notContains";
	
	public static final String EQUALS = "equals";
	
	public static final String NOT_EQUALS = "notEquals";
	
	// FORMATOS FECHAS
	
	public static final String DD_MM_YYYY = "dd/MM/yyyy";
	
	public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	
	// NOMBRE PESTAÑAS EXCEL
	
	public static final String SHEET_LOGS = "Logs";
	
	public static final String SHEET_CONFIGURACION = "Configuración";
	
	public static final String SHEET_USUARIOS = "Usuarios";
	
	// CONTENT TYPE
	
	public static final String CONTENT_EXCEL = "application/vnd.ms-excel";
	
	// ATTACHMENTS
	
	public static final String ATTACHMENTS_EXCEL = "attachment; filename=reporte.xlsx";
	
	
			
}
