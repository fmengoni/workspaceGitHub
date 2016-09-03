package com.telnetar.restcontrollers;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.telnetar.Constants;
import com.telnetar.Util;
import com.telnetar.model.Luminaria;
import com.telnetar.model.User;
import com.telnetar.nodejs.NodeConnectDto;
import com.telnetar.nodejs.NodeSetValueDto;
import com.telnetar.nodejs.TelnetarIOCallback;
import com.telnetar.services.LuminariaAuditService;
import com.telnetar.services.LuminariaService;
import com.telnetar.view.model.DatatableData;

import io.socket.IOAcknowledge;
import io.socket.SocketIO;

@RestController
@RequestMapping(value="/luminarias")
public class LuminariasRestController {
	@Autowired
	LuminariaService luminariaService;
	@Autowired
	LuminariaAuditService luminariaAuditService;
	
	@RequestMapping(value="/luminariasNivelPais/{edit}")
	public ResponseEntity<DatatableData> luminariasNivelPais(@PathVariable Long edit){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Luminaria> lsLuminarias = luminariaService.obtenerLuminariasNivelPais(username);
		DatatableData datatableData = new DatatableData();
		
		makeReturn(lsLuminarias, datatableData, Constants.LEVEL_COUNTRY, edit.equals(new Long(1)));
		
 		return new ResponseEntity<DatatableData>(datatableData, HttpStatus.OK);
	}

	@RequestMapping(value="/luminariasNivelProvincia/{edit}")
	public ResponseEntity<DatatableData> luminariasNivelProvincia(@PathVariable Long edit){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Luminaria> lsLuminarias = luminariaService.obtenerLuminariasNivelProvincia(username);
		DatatableData datatableData = new DatatableData();
		
		makeReturn(lsLuminarias, datatableData, Constants.LEVEL_PROVINCE, edit.equals(new Long(1)));
		
		return new ResponseEntity<DatatableData>(datatableData, HttpStatus.OK);
	}
	
	@RequestMapping(value="/luminariasNivelPartido/{edit}")
	public ResponseEntity<DatatableData> luminariasNivelPartido(@PathVariable Long edit){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Luminaria> lsLuminarias = luminariaService.obtenerLuminariasNivelPartido(username);
		DatatableData datatableData = new DatatableData();
		
		makeReturn(lsLuminarias, datatableData, Constants.LEVEL_PART, edit.equals(new Long(1)));
		
		return new ResponseEntity<DatatableData>(datatableData, HttpStatus.OK);
	}
	
	@RequestMapping(value="/luminariasNivelLocalidad/{edit}")
	public ResponseEntity<DatatableData> luminariasNivelLocalidad(@PathVariable Long edit){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Luminaria> lsLuminarias = luminariaService.obtenerLuminariasNivelLocalidad(username);
		DatatableData datatableData = new DatatableData();
		
		makeReturn(lsLuminarias, datatableData, Constants.LEVEL_LOCATION, edit.equals(new Long(1)));
		
		return new ResponseEntity<DatatableData>(datatableData, HttpStatus.OK);
	}
	
	@RequestMapping(value="/luminariasNivelNodo/{edit}")
	public ResponseEntity<DatatableData> luminariasNivelNodo(@PathVariable Long edit){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Luminaria> lsLuminarias = luminariaService.obtenerLuminariasNivelNodo(username);
		DatatableData datatableData = new DatatableData();
		
		makeReturn(lsLuminarias, datatableData, Constants.LEVEL_NODO, edit.equals(new Long(1)));
		
		return new ResponseEntity<DatatableData>(datatableData, HttpStatus.OK);
	}
	
	@RequestMapping(value="/luminariasPorNodo/{pkNodo}/{edit}")
	public ResponseEntity<DatatableData> luminariasPorNodo(@PathVariable Long pkNodo, @PathVariable Long edit){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Luminaria> lsLuminarias = luminariaService.obtenerLuminariasPorNodo(username, pkNodo);
		DatatableData datatableData = new DatatableData();
		
		makeReturn(lsLuminarias, datatableData, Constants.LEVEL_LUMINARIA, edit.equals(new Long(1)));
		
		return new ResponseEntity<DatatableData>(datatableData, HttpStatus.OK);
	}
	
	@RequestMapping(value="/infoLuminaria/{pkLuminaria}")
	public Luminaria infoLuminaria(@PathVariable Long pkLuminaria){
		Luminaria luminaria = luminariaService.findOne(pkLuminaria);
		luminaria.setUltimaAuditoria(luminariaAuditService.obtenerUltimaAuditoria(luminaria.getHight(), luminaria.getLow()));
		return luminaria;
	}
	
	@RequestMapping(value="/agregar/{pkNodo}/{hight}/{low}/{lat}/{lng}/{descripcion}", method=RequestMethod.PUT)
	public ResponseEntity<Luminaria> agregarLuminaria(@PathVariable Long pkNodo, @PathVariable Integer hight, @PathVariable Integer low, @PathVariable Double lat, @PathVariable Double lng, @PathVariable String descripcion){
		Luminaria luminaria = new Luminaria(pkNodo, descripcion, hight, low, lat, lng);
		luminaria = luminariaService.save(luminaria);
		return new ResponseEntity<Luminaria>(luminaria, HttpStatus.OK);
	}
	
	@RequestMapping(value="/setIntensity", method=RequestMethod.POST)
	public ResponseEntity<Luminaria> setIntensity(HttpServletRequest request){
		String pkLumi = request.getParameter("pkLumi");
		String intensity = request.getParameter("intensity");
		
		Luminaria luminaria = luminariaService.findOne(new Long(pkLumi));
		try{
			nodeSetValue(
				    luminaria.getHight().byteValue(), luminaria.getLow().byteValue(), 
				    Util.getVirtualIntensity(Integer.parseInt(intensity)).byteValue(),
			    // new String[]{NODO_NAME});
				    new String[] { luminaria.nodoId() },
				    request);
			return new ResponseEntity<Luminaria>(luminaria, HttpStatus.OK);
		}catch (MalformedURLException e) {
			return new ResponseEntity<Luminaria>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private void nodeSetValue(byte hightByte, byte lowByte, byte intensity, String[] targets, HttpServletRequest req) throws MalformedURLException {
		Gson gson = new Gson();
		SocketIO socketio = ((SocketIO)req.getSession().getAttribute("socketio")); 
		User user = (User) req.getSession().getAttribute("user");
		if(!socketio.isConnected()){
		    String json = gson.toJson(new NodeConnectDto("Web", SecurityContextHolder.getContext().getAuthentication().getName()));
		    Properties properties = new Properties();
		    properties.put("json", json);
		    req.getSession().setAttribute("socketio", new SocketIO(Constants.NODE_JS_SERVER_LINK, properties));
		    
//		    getUserSessionBean().setMyIOCallback(new MyIOCallback(getUserSessionBean().getSocketIO()));
		    socketio.connect(new TelnetarIOCallback());
		}
		String json = gson.toJson(new NodeSetValueDto(user.getPk(), user.getToken(), hightByte, lowByte, intensity, targets));
		socketio.emit(
		    "setValue", 
		    new IOAcknowledge(){
				@Override
				public void ack(Object... arg0) {
					System.out.println(arg0);
				}
		    },
		    json
		);
	}
	
	private void makeReturn(List<Luminaria> lsLuminarias, DatatableData datatableData, Integer nivel, Boolean edit) {
		for (Luminaria luminaria : lsLuminarias) {
			luminaria.setUltimaAuditoria(luminariaAuditService.obtenerUltimaAuditoria(luminaria.getHight(), luminaria.getLow()));
			List<String> data = new ArrayList<String>();
			data.add(luminaria.getHight() + "-" + luminaria.getLow());
			data.add(luminaria.getUbicacion(nivel));
			if(!edit){
				data.add(luminaria.getUltimaAuditoria() != null ? Util.getRealIntensity(luminaria.getUltimaAuditoria().getIntensity()).toString() : "No hay registro");
				data.add(luminaria.getDescripcion().trim().toUpperCase());
				data.add(luminaria.getUltimaAuditoria() != null ? new Integer(Util.getTemperature(luminaria.getUltimaAuditoria().getTempHight(), luminaria.getUltimaAuditoria().getTempLow())).toString() : "No hay registro");
				data.add(luminaria.getUltimaAuditoria() != null ? new Integer(Util.getTemperature(luminaria.getUltimaAuditoria().getLumiContextH(), luminaria.getUltimaAuditoria().getLumiContextL())).toString() : "No hay registro" );
				data.add(luminaria.getUltimaAuditoria() != null ? Util.formatDate(luminaria.getUltimaAuditoria().getFeRegistro()) : "No hay registro");
				
				data.add("<a data-toggle='modal' href='#lumiPopupPanel' class='btn btn-default btn-block' onclick='obtenerInfoLuminaria("+luminaria.getPk()+")'>Administrar</a>");	
			}
			
			datatableData.getData().add(data);
		}
	}
}
