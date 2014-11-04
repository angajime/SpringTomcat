package hello;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by tarazaky on 3/11/14.
 */
@Service
public class AlertLookupService {

    @Async
    public Future<List<Alert>> findAlert(JdbcTemplate jdbcTemplate, String urn, Map<String, String> params){

        for(Map.Entry<String, String> entry : params.entrySet()){
            List<Alert> results = jdbcTemplate.query(
                    "select * from alerts where urn = '" +
                            urn+"'"+"and resource = '"+ entry.getKey() + "'",
                    new RowMapper<Alert>() {
                        List<String> ls = new LinkedList<String>();
                        @Override
                        public Alert mapRow(ResultSet rs, int rowNum) throws SQLException {
                            ls.add(rs.getString("recipient"));
                            return new Alert(rs.getLong("id"),
                                    rs.getString("urn"),
                                    rs.getString("resource"),
                                    rs.getString("comparator"),
                                    rs.getDouble("value"),
                                    ls,
                                    rs.getBoolean("active")
                            );
                        }
                    }
            );

            for(Alert a : results){
                String comp = a.getComparator();
                Boolean evaluate = false;
                if(comp.equals("<")){
                    evaluate = Double.parseDouble(entry.getValue()) < a.getValue();
                }else if(comp.equals(">")){
                    evaluate = Double.parseDouble(entry.getValue()) > a.getValue();
                }else if(comp.equals("=")){
                    evaluate = Double.parseDouble(entry.getValue()) == a.getValue();
                }else if(comp.equals("<=")){
                    evaluate = Double.parseDouble(entry.getValue()) <= a.getValue();
                }else if(comp.equals(">=")){
                    evaluate = Double.parseDouble(entry.getValue()) >= a.getValue();
                }
                a.setActive(evaluate);
            }
        }








        return new AsyncResult<List<Alert>>(null);
    }
}
