/*******************************************************************************
 * Fiware IdM Connector
 *   Copyright (C) 2019 Engineering Ingegneria Informatica S.p.A.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package it.eng.opsi.authentication.fiware;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import it.eng.opsi.authentication.fiware.model.Token;

public class TokenDeserializer implements JsonDeserializer<Token> {

	public Token deserialize(JsonElement json, Type arg1, JsonDeserializationContext context)
			throws JsonParseException {

		final JSONObject jsonObject = new JSONObject(json.toString());
		Set<String> scopeSet = new HashSet<String>();
		if (jsonObject.has("scope")) {
			Object scope = jsonObject.get("scope");

			if (scope instanceof JSONArray) {
				try {
					for (Object scopeElement : ((JSONArray) scope)) {
						scopeSet.add((String) scopeElement);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (scope instanceof String) {
				scopeSet.add((String) scope);
			} else {
				scopeSet.add(scope.toString());
			}
		}

		return new Token(jsonObject.optString("access_token", ""), jsonObject.optString("token_type", ""),
				jsonObject.optInt("expires_in", 0), jsonObject.optString("refresh_token", ""), scopeSet,
				jsonObject.optString("state", ""));
	}

}
