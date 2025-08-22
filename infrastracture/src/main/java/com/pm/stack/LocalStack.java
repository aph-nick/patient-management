package com.pm.stack;

import com.amazonaws.services.ec2.model.Vpc;
import software.amazon.awscdk.*;

public class LocalStack extends Stack {
    private final Vpc vpc;

    public LocalStack(App scope, final String id, final StackProps props){
        super(scope, id, props);

        this.vpc = createVpc();
    }

    private Vpc createVpc(){
        Vpc.Builder
                .create(this, "PatientManagementVPC")
                .vpcName("PatientManagementVPC")
                .maxAzs(2)
                .build;
    }

    public static void main (final String[] args) {
        App app = new App(AppProps.builder().outdir("./cdk.out").build());
        StackProps props = StackProps.builder()
                .synthesizer(new BootstraplessSynthesizer())
                .build();

        new LocalStack(app, "localstack", props);
        app.synth();
        System.out.println("App synthesizing in progress....");
    }
}
