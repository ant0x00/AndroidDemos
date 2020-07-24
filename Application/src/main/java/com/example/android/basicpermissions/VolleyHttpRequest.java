package com.example.android.basicpermissions;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class VolleyHttpRequest extends JsonRequest<JSONObject> {
	private Header[] headers;
	/**
	 * Creates a new request.
	 * 
	 * @param method
	 *            the HTTP method to use
	 * @param url
	 *            URL to fetch the JSON from
	 * @param requestBody
	 *            A {@link String} to post with the request. Null is allowed and
	 *            indicates no parameters will be posted along with request.
	 * @param listener
	 *            Listener to receive the JSON response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
	public VolleyHttpRequest(int method, String url, String requestBody,
                             Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, requestBody, listener, errorListener);
	}

	/**
	 * Creates a new request.
	 * 
	 * @param url
	 *            URL to fetch the JSON from
	 * @param listener
	 *            Listener to receive the JSON response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
	public VolleyHttpRequest(String url, Listener<JSONObject> listener,
                             ErrorListener errorListener) {
		super(Method.GET, url, null, listener, errorListener);
	}

	/**
	 * Creates a new request.
	 * 
	 * @param method
	 *            the HTTP method to use
	 * @param url
	 *            URL to fetch the JSON from
	 * @param listener
	 *            Listener to receive the JSON response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
	public VolleyHttpRequest(int method, String url,
                             Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, null, listener, errorListener);
	}
	/**
	 * Creates a new request.
	 * 
	 * @param method
	 *            the HTTP method to use
	 * @param url
	 *            URL to fetch the JSON from
	 * @param jsonRequest
	 *            A {@link JSONObject} to post with the request. Null is allowed
	 *            and indicates no parameters will be posted along with request.
	 * @param listener
	 *            Listener to receive the JSON response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
	public VolleyHttpRequest(int method, String url, JSONObject jsonRequest,
                             Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, (jsonRequest == null) ? null : jsonRequest
				.toString(), listener, errorListener);
	}

	/**
	 * Constructor which defaults to <code>GET</code> if
	 * <code>jsonRequest</code> is <code>null</code>, <code>POST</code>
	 * otherwise.
	 * 
	 */
	public VolleyHttpRequest(String url, JSONObject jsonRequest,
                             Listener<JSONObject> listener, ErrorListener errorListener) {
		this(jsonRequest == null ? Method.GET : Method.POST, url, jsonRequest,
				listener, errorListener);
	}
	
	/**
	 * Creates a new request.
	 * 
	 * @param headers
	 *            the HTTP header
	 * @param url
	 *            URL to fetch the JSON from
	 * @param jsonRequest
	 *            A {@link JSONObject} to post with the request. Null is allowed
	 *            and indicates no parameters will be posted along with request.
	 * @param listener
	 *            Listener to receive the JSON response
	 * @param errorListener
	 *            Error listener, or null to ignore errors.
	 */
    public VolleyHttpRequest(Header[] headers, String url, JSONObject jsonRequest,
                             Listener<JSONObject> listener, ErrorListener errorListener) {
		this(Request.Method.POST, url, jsonRequest, listener, errorListener);
		this.headers = headers;

	}
    
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

    	Map<String, String> HeaderMap = new HashMap<String, String>();
    	if (headers != null) {
    		for(Header midh:headers){
    			HeaderMap.put(midh.getName(), midh.getValue());
    		}
    	} else {
    		return super.getHeaders();
    	}
    	return HeaderMap;
    }


	private int getShort(byte[] data) {
		return (int) ((data[0] << 8) | data[1] & 0xFF);
	}

	protected String getRealString(byte[] data) {
		byte[] h = new byte[2];
		h[0] = (data)[0];
		h[1] = (data)[1];
		int head = getShort(h);
		boolean t = head == 0x1f8b;
		InputStream in;
		StringBuilder sb = new StringBuilder();
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			if (t) {
				in = new GZIPInputStream(bis);
			} else {
				in = bis;
			}
			BufferedReader r = new BufferedReader(new InputStreamReader(in),
					1000);
			for (String line = r.readLine(); line != null; line = r.readLine()) {
				sb.append(line);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
//			String jsonString = new String((response.data),
//					HttpHeaderParser.parseCharset(response.headers,
//							PROTOCOL_CHARSET));
			String jsonString = getRealString(response.data) + HttpHeaderParser.parseCharset(response.headers,PROTOCOL_CHARSET);
			
			return Response.success(new JSONObject(jsonString),
					HttpHeaderParser.parseCacheHeaders(response));
//		} catch (UnsupportedEncodingException e) {
//			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}

}
