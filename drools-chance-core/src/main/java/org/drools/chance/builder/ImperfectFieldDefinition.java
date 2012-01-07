/*
 * Copyright 2011 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.chance.builder;

import org.drools.base.ClassFieldAccessor;
import org.drools.base.FieldAccessor;
import org.drools.chance.common.ImperfectField;
import org.drools.core.util.StringUtils;
import org.drools.factmodel.AnnotationDefinition;
import org.drools.factmodel.ClassDefinition;
import org.drools.factmodel.FieldDefinition;
import org.drools.reteoo.builder.BuildUtils;

public class ImperfectFieldDefinition extends FieldDefinition {

    private String impKind;
    private String impType;
    private String degreeType;
    private String support;


    private int history;

    private ClassDefinition klassDef;

    public void setClassDefinition(ClassDefinition classDef) {
        klassDef = classDef;
    }

    public ImperfectFieldDefinition(String name, String type) {
        super( name , type );
    }


    public String getImpKind() {
        return impKind;
    }

    public void setImpKind(String impKind) {
        this.impKind = impKind;
    }

    public String getImpType() {
        return impType;
    }

    public void setImpType(String impType) {
        this.impType = impType;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }



    public Class< ? > getType() {

        String prefix = "boolean".equals( getTypeName() ) ? "is" : "get";
        String name = prefix + getName().substring(0,1).toUpperCase() + getName().substring(1) + "Value";

        try {
            return klassDef.getDefinedClass().getMethod( name ).getReturnType();
        } catch ( Exception e ) {
            e.printStackTrace();
            return Object.class;
        }
    }



    public static ImperfectFieldDefinition fromField( FieldDefinition field, AnnotationDefinition ia ) {

        ImperfectFieldDefinition ifldDef = new ImperfectFieldDefinition( field.getName(), field.getTypeName() );
        ifldDef.setImpKind( (String) ia.getValues().get("kind").getValue() );
        ifldDef.setImpType( (String) ia.getValues().get("type").getValue() );
        if ( ia.getValues().containsKey( "history" ) ) {
            ifldDef.setHistory( (Integer) ia.getValues().get("history").getValue() );
        }
        ifldDef.setDegreeType( (String) ia.getValues().get("degree").getValue() );
        if ( ia.getValues().containsKey( "support" ) ) {
            ifldDef.setSupport( (String) ia.getValues().get("support").getValue() );
        }
        if ( ! StringUtils.isEmpty( field.getInitExpr() ) ) {
            ifldDef.setInitExpr( field.getInitExpr().replace( "\"","" ) );
        }
        return ifldDef;

    }



}