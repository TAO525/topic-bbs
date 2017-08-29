<style>
    html{
        height: 100%;
    }
    body {
        background-image: url("/images/bg2.jpg");

        background-size: cover;

        background-position: 50% 0;

        height: 100% ;
    }
</style>
<script src="/js/jquery.ripples.js"></script>
<script>
    $(document).ready(function() {

        try {

            $('body').ripples({

                resolution: 512,

                dropRadius: 20, //px

                perturbance: 0.04

            });



        }

        catch (e) {

            $('.error').show().text(e);

        }

    });
</script>
