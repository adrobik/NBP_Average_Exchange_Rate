package atj.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import atj.Average;
import atj.Rate;
import atj.Rates;

@Path("/5012/exchangerates/rates")
public class NBPAverageService {

	@GET
	@Path("{table}/{code}/{topCount}")
	@Produces(MediaType.APPLICATION_XML)
	public Average getAverageXML(@PathParam("table") String table, @PathParam("code") String code,
			@PathParam("topCount") String topCount) {
		NBPRestClient cl = new NBPRestClient();
		Rates result = cl.getXMLData(table, code, topCount);
		Average avg = new Average();
		avg.setCurrency(code);
		if (result.getRates().size() <= 0) {
			avg.setAverage(0);
			return avg;
		} else if (result.getRates().size() == 1) {
			avg.setAverage(result.getRates().get(0).getMid());
			return avg;
		} else {
			double sum = 0;
			for (Rate r : result.getRates()) {
				sum += r.getMid();
			}
			avg.setAverage(sum / result.getRates().size());
			return avg;
		}
	}

//	@GET
//	@Path("{table}/{code}/{topCount}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getAverageJSON(@PathParam("table") String table, @PathParam("code") String code,
//			@PathParam("topCount") String topCount) {
//		return "";
//	}
}
